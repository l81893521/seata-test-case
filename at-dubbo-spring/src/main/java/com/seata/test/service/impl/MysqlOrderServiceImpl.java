package com.seata.test.service.impl;

import com.seata.test.entity.Order;
import com.seata.test.service.AccountService;
import com.seata.test.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

/**
  * @author will.zjw
  * @date 2019-04-19 16:26
  */
@Slf4j
public class MysqlOrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private JdbcTemplate jdbcTemplate;

    private AccountService accountService;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gt-seata-for-success")
    public void testSeataForSuccess() {
        int orderId = insertOrder("U0000010", "C00321", 2, 400);
        updateOrder(orderId, 300);
        deleteOrder(orderId);
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gt-seata-for-success")
    public void testSeataForFail() {
        int orderId = insertOrder("U0000010", "C00321", 2, 400);
        updateOrder(orderId, 300);
        deleteOrder(orderId);
        throw new RuntimeException("操作失败");
    }

    @Override
    public int insertOrder(String userId, String commodityCode, int orderCount, int money) {
        jdbcTemplate.update("insert into seata_order_tbl(user_id, commodity_code, count, money) values (?,?,?,?)", userId, commodityCode, orderCount, money);
        Integer pk = jdbcTemplate.queryForObject("select max(id) from seata_order_tbl", Integer.class);
        return pk;
    }

    @Override
    public void updateOrder(int orderId, int money) {
        jdbcTemplate.update("update seata_order_tbl set money = money - ? where id = ?", money, orderId);
    }

    @Override
    public void updateJoinOrderStatus(int orderId, String userId, boolean shouldThrowException) {
        jdbcTemplate.update("update order_tbl o " +
                "left join account_tbl a on o.user_id = a.user_id " +
                "set o.order_status = 1 " +
                "where o.user_id = ? and o.id = ?",
                userId, orderId);

        if (shouldThrowException) {
            throw new RuntimeException("update join order status failed.");
        }
        log.info("update join order status success");
    }

    @Override
    public void deleteOrder(int orderId) {
        jdbcTemplate.update("delete from seata_order_tbl where id = ?", orderId);
    }

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {
        // 计算订单金额
        int orderMoney = calculate(commodityCode, orderCount);

        // 从账户余额扣款
        accountService.debit(userId, orderMoney, false);

        final Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement pst = connection.prepareStatement(
                "insert into seata_order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            pst.setObject(1, order.userId);
            pst.setObject(2, order.commodityCode);
            pst.setObject(3, order.count);
            pst.setObject(4, order.money);
            return pst;
        }, keyHolder);

        order.id = keyHolder.getKey().longValue();

        return order;
    }

    private int calculate(String commodityId, int orderCount) {
        return 200 * orderCount;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
