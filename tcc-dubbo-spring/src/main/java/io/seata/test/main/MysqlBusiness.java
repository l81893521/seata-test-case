package io.seata.test.main;

import io.seata.test.ApplicationKeeper;
import io.seata.test.service.BusinessService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-24 14:30
  */
public class MysqlBusiness {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring/dubbo-business.xml"});

        final BusinessService businessService = (BusinessService) context.getBean("businessService");

        String userId = "U100002";

        try {
            //businessService.purchase(userId, "", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
