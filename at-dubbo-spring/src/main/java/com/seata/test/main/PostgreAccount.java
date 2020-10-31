package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import com.seata.test.service.AccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
  */
public class PostgreAccount {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-account-postgre-service.xml"});

        AccountService accountService = (AccountService)context.getBean("accountService");

        int id = 19;
        String userId = "U100002";
        int initMoney = 1000;
        int debitMoney = 10;

        try {
            //新增
            //accountService.createAccount(userId, initMoney, false);
            //accountService.createAccount(userId, initMoney, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //普通修改
            //accountService.debit(userId, debitMoney, false);
            //accountService.debit(userId, debitMoney, true);
        } catch (Exception e){
            e.printStackTrace();
        }


        try {
            //普通删除
            //accountService.deleteAccount(userId, false);
            //accountService.deleteAccount(userId, true);
        } catch (Exception e){
            e.printStackTrace();
        }


        try {
            //普通查询锁
            accountService.forUpdate(id, false);
            accountService.forUpdate(id, true);
        } catch (Exception e){
            e.printStackTrace();
        }


        new ApplicationKeeper(context).keep();
    }
}
