package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import com.seata.test.service.AccountService;
import com.seata.test.service.impl.OracleAccountServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
  */
public class OracleAccount {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-account-oracle-service.xml"});

        AccountService accountService = (AccountService) context.getBean("accountService");

        int id = 144;
        String userId = "U100002";
        int debitMoney = 10;

        //普通新增
        try {
            //accountService.createAccount(userId, debitMoney, false);
            //accountService.createAccount(userId, debitMoney, true);
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
            //普通删除
            //accountService.deleteAccount(userId, false);
            //accountService.deleteAccount(userId, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //普通查询锁
            //accountService.forUpdate(id, false);
            //accountService.forUpdate(id, true);
        } catch (Exception e){
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
