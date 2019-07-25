package com.seata.test.service.impl;

import com.seata.test.service.AccountService;
import com.seata.test.service.BusinessService;
import com.seata.test.service.OrderService;
import com.seata.test.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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
public class BusinessServiceImpl implements BusinessService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);

    private StorageService storageService;

    private OrderService orderService;

    private AccountService accountService;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-demo-tx")
    public void purchase(String userId, String commodityCode, int orderCount) {
        logger.info("purchase begin ... xid: " + RootContext.getXID());

        storageService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
        //制造异常
        throw new RuntimeException("xxx");
    }

    /**
     * 创建账户
     *
     * @param userId
     * @param money
     */
    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "create-account-tx")
    public void createAccount(String userId, int money) {
        accountService.createAcount(userId, money);
        throw new RuntimeException("创建账户失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "delete-account-tx")
    public void deleteAccount(String userId) {
        accountService.deleteAccount(userId);
        throw new RuntimeException("删除账户失败");
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
