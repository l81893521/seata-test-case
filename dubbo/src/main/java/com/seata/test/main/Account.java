package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

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
public class Account {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-account-service.xml"});
        context.getBean("accountService");
        JdbcTemplate accountJdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        accountJdbcTemplate.update("delete from seata_account_tbl where user_id = 'U100001'");
        accountJdbcTemplate.update("insert into seata_account_tbl(user_id, money) values ('U100001', 999)");

        new ApplicationKeeper(context).keep();
    }
}
