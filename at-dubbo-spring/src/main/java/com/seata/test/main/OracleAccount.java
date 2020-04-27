package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import com.seata.test.service.AccountService;
import com.seata.test.service.impl.OracleAccountServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
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
public class OracleAccount {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-account-oracle-service.xml"});

        AccountService accountService = context.getBean("accountService", OracleAccountServiceImpl.class);

        int id = 144;
        String userId = "U100002";
        int debitMoney = 10;

        //普通新增
//        try {
//            accountService.createAccount(userId, debitMoney);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            //普通修改
//            accountService.debit(userId, debitMoney);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            //in修改
//            accountService.debitWithIn(userId, debitMoney);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            //between修改
//            accountService.debitWithBetween(userId, debitMoney);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            //exists修改
//            accountService.debitWithExist(userId, debitMoney);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            //not exists修改
//            accountService.debitWithNotExist(userId, debitMoney);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            //普通删除
//            accountService.deleteAccount(userId);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
//            //in删除
//            accountService.deleteAccountWithIn(userId);
//        } catch (Exception e){
//            e.printStackTrace();
//        }

//        try {
//            //between删除
//            accountService.deleteAccountWithBetween(id);
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        try {
            //普通查询锁
            accountService.forUpdate(id);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //in查询锁
            accountService.forUpdateWithIn(id);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            //between查询锁
            accountService.forUpdateWithBetween(id);
        } catch (Exception e){
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
