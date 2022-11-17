package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import com.seata.test.service.AccountService;
import com.seata.test.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
  */
public class MysqlTestCase {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-mysql-service.xml"});

        AccountService accountService = (AccountService) context.getBean("accountService");
        OrderService orderService = (OrderService) context.getBean("orderService");

//        AccountService accountServiceProxy = context.getBean("proxyFactoryBean", AccountService.class);
        int id = 1;
        int orderId = 1;
        String userId = "U100002";
        int debitMoney = 10;

        try {
            // insert
//            accountService.createAccount(userId, 999, false);
//            accountService.createAccount(userId, 999, true);
            //accountServiceProxy.createAccount(userId, 999);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            // insert on duplicate
//            accountService.createOrUpdateAccount(userId, true );
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //普通修改
//            accountService.debit(userId, debitMoney, false);
//            accountService.debit(userId, debitMoney, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //删除
//            accountService.deleteAccount(userId, false);
//            accountService.deleteAccount(userId, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            // check lock
//            accountService.forUpdate(id, false);
//            accountService.forUpdate(id, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            orderService.updateJoinOrderStatus(orderId, userId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
