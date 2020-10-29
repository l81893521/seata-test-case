package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import com.seata.test.service.AccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
  */
public class MysqlAccount {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-account-mysql-service.xml"});

        AccountService accountService = (AccountService) context.getBean("accountService");
//        AccountService accountServiceProxy = context.getBean("proxyFactoryBean", AccountService.class);
        int id = 1;
        String userId = "U100002";
        String userId2 = "U100003";
        int debitMoney = 10;

        try {
            //普通新增
            accountService.createAccount(userId, 999, false);
            accountService.createAccount(userId, 999, true);
            //accountServiceProxy.createAccount(userId, 999);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //普通修改
            accountService.debit(userId, debitMoney, false);
            accountService.debit(userId, debitMoney, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //删除
            accountService.deleteAccount(userId, false);
            accountService.deleteAccount(userId, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //查询锁
            accountService.forUpdate(id, false);
            accountService.forUpdate(id, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
