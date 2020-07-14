package io.seata.test.main;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.test.ApplicationKeeper;
import io.seata.test.service.AccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
  */
public class MysqlAccount {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-account-mysql-service.xml"});

        AccountService accountService = (AccountService) context.getBean("accountService");

        int id = 65;
        String userId = "U100002";
        String userId2 = "U100003";
        int debitMoney = 10;

        try {
            //普通新增
//            accountService.debit(new BusinessActionContext(), userId, debitMoney);
        } catch (Exception e){
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
