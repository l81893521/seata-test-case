package com.seata.test.service.impl;

import com.seata.test.service.AccountService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
  * @author will.zjw
  * @date 2019-10-25 14:15
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
public class PostgreAccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    public void forUpdate(int id) {

    }

    @Override
    public void forUpdateWithIn(int id) {

    }

    @Override
    public void forUpdateWithBetween(int id) {

    }

    @Override
    public void debit(String userId, int money) {

    }

    @Override
    public void debitWithIn(String userId, int money) {

    }

    @Override
    public void debitWithBetween(String userId, int money) {

    }

    @Override
    public void debitWithExist(String userId, int money) {

    }

    @Override
    public void debitWithNotExist(String userId, int money) {

    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-create-account")
    public void createAccount(String userId, int money) {
        jdbcTemplate.update("insert into account_tbl(id, user_id, money) values (nextval('seq_account_tbl_id'), ?, ?)", userId, money);
        throw new RuntimeException("创建账户失败");
    }

    @Override
    public void createAccountWithPk(int id, String userId, int money) {

    }

    @Override
    public void deleteAccount(String userId) {

    }

    @Override
    public void deleteAccountWithIn(String userId) {

    }

    @Override
    public void deleteAccountWithBetween(int id) {

    }

    @Override
    public void updateAccountInformation(String userId, String information) {

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
