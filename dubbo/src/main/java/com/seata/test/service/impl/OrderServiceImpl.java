package com.seata.test.service.impl;

import com.seata.test.entity.Order;
import com.seata.test.service.AccountService;
import com.seata.test.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
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
  *            佛主保佑,不要出BUG
  *                 _ooOoo_
  *                o8888888o
  *                88" . "88
  *                (| -_- |)
  *                O\  =  /O
  *             ____/`---'\____
  *           .'  \\|     |//  `.
  *          /  \\|||  :  |||//  \
  *         /  _||||| -:- |||||-  \
  *         |   | \\\  -  /// |   |
  *         | \_|  ''\---/''  |   |
  *          \  .-\__  `-`  ___/-. /
  *        ___`. .'  /--.--\  `. . __
  *      ."" '<  `.___\_<|>_/___.'  >'"".
  *    | | :  `- \`.;`\ _ /`;.`/ - ` : | |
  *    \  \ `-.   \_ __\ /__ _/   .-` /  /
  * ======`-.____`-.___\_____/___.-`____.-'======
  *                 `=---='
  */
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private JdbcTemplate jdbcTemplate;

    private AccountService accountService;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "insert-order-global-tx")
    public void insertOrder(String userId, String commodityCode, int orderCount, int money) {
        jdbcTemplate.update("insert into `order`(user_id, commodity_code, count, money) values (?,?,?,?)", userId, commodityCode, orderCount, money);
        throw new RuntimeException("插入订单失败");
    }

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {
        // 计算订单金额
        int orderMoney = calculate(commodityCode, orderCount);

        // 从账户余额扣款
        accountService.debit(userId, orderMoney);

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
