package com.seata.test.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.seata.test.service.BusinessService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-24 14:30
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
public class Business {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring/dubbo-business.xml"});
        final BusinessService businessService = (BusinessService) context.getBean("businessService");
        //businessService.purchase("U100001", "C00321", 2);
        //businessService.deleteAccount("U100009");
        //businessService.createAccount("U100004", 999);
        //businessService.debit("U100009", 0);
        businessService.debitByDiffentDataSource("U100009", 100);
        //businessService.updateAccountInformation("U100009", "hello world");
    }
}
