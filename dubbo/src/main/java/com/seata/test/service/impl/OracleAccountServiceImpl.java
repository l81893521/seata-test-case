package com.seata.test.service.impl;

import com.seata.test.service.AccountService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.jdbc.core.JdbcTemplate;

/**
  * @author will.zjw
  * @date 2019-09-26 17:16
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
public class OracleAccountServiceImpl implements AccountService {

    private JdbcTemplate oracleAccountJdbcTemplate;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-account-for-update")
    public void forUpdate(int id) {
        oracleAccountJdbcTemplate.queryForList("select * from account_tbl where id = ? for update", id);
        String sql = "select * from account_tbl where id = " + id + " for update";
        oracleAccountJdbcTemplate.queryForList(sql);
        throw new RuntimeException("查询锁失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-account-for-update-with-in")
    public void forUpdateWithIn(int id) {
        oracleAccountJdbcTemplate.queryForList("select * from account_tbl where id in (?) for update", id);
        String sql = "select * from account_tbl where id in (" + id + ") for update";
        oracleAccountJdbcTemplate.queryForList(sql);
        throw new RuntimeException("查询锁失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-account-for-update-with-between")
    public void forUpdateWithBetween(int id) {
        oracleAccountJdbcTemplate.queryForList("select * from account_tbl where id between ? and ? for update", id, id);
        String sql = "select * from account_tbl where id between " + id + " and " + id + " for update";
        oracleAccountJdbcTemplate.queryForList(sql);
        throw new RuntimeException("查询锁失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit")
    public void debit(String userId, int money) {
        oracleAccountJdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        String sql = "update account_tbl set money = money - " + money + " where user_id = '" + userId + "'";
        oracleAccountJdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit-with-in")
    public void debitWithIn(String userId, int money) {
        oracleAccountJdbcTemplate.update("update account_tbl set money = money - ? where user_id in (?)", new Object[] {money, userId});
        String sql = "update account_tbl set money = money - " + money + " where user_id in ('" + userId + "')";
        oracleAccountJdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit-with-between")
    public void debitWithBetween(String userId, int money) {
        oracleAccountJdbcTemplate.update("update account_tbl set money = money - ? where user_id between ? and ?", new Object[] {money, userId, userId});
        String sql = "update account_tbl set money = money - " + money + " where user_id between '" + userId + "' and '" + userId + "'";
        oracleAccountJdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    public void platformDebit(String userId, int money) {

    }

    @Override
    public void createAccount(String userId, int money) {
        oracleAccountJdbcTemplate.update("insert into account_tbl(id, user_id, money, information, description) values (account_tbl_seq.nextval, ?, ?, ?, ?)", userId, money, "a", "a");
    }

    @Override
    public void createAccountWithPk(int id, String userId, int money) {

    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account")
    public void deleteAccount(String userId) {
        oracleAccountJdbcTemplate.update("delete from account_tbl where user_id = ?", userId);
        String sql = "delete from account_tbl where user_id = \'" + userId + "\'";
        oracleAccountJdbcTemplate.update(sql);
        throw new RuntimeException("账户删除失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account-with-in")
    public void deleteAccountWithIn(String userId) {
        oracleAccountJdbcTemplate.update("delete from account_tbl where user_id in (?)", userId);
        String sql = "delete from account_tbl where user_id in (\'" + userId + "\')";
        oracleAccountJdbcTemplate.update(sql);
        throw new RuntimeException("账户删除失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account-with-between")
    public void deleteAccountWithBetween(int id) {
        oracleAccountJdbcTemplate.update("delete from account_tbl where id between ? and ?", id, id);
        String sql = "delete from account_tbl where id between " + id + " and " + id;
        oracleAccountJdbcTemplate.update(sql);
        throw new RuntimeException("账户删除失败");
    }

    @Override
    public void updateAccountInformation(String userId, String information) {

    }

    public void setOracleAccountJdbcTemplate(JdbcTemplate oracleAccountJdbcTemplate) {
        this.oracleAccountJdbcTemplate = oracleAccountJdbcTemplate;
    }
}
