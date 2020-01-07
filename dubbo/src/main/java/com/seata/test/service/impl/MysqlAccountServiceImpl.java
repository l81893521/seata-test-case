package com.seata.test.service.impl;


import com.seata.test.service.AccountService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
  * @author will.zjw
  * @date 2019-04-19 16:25
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
public class MysqlAccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-account-for-update")
    public void forUpdate(int id) {
        jdbcTemplate.queryForList("select * from seata_account_tbl where id = ? for update", id);
        jdbcTemplate.queryForList("select * from seata_account_TBL where id = ? for update", id);
        jdbcTemplate.queryForList("select * from `seata_account_tbl` where id = ? for update", id);
        jdbcTemplate.queryForList("select * from locals_production.seata_account_tbl where id = ? for update", id);
        jdbcTemplate.queryForList("select * from locals_production.`seata_account_tbl` where id = ? for update", id);
        throw new RuntimeException("查询锁失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-account-for-update-with-in")
    public void forUpdateWithIn(int id) {
        jdbcTemplate.queryForList("select * from seata_account_tbl where id in (?) for update", id);
        jdbcTemplate.queryForList("select * from seata_account_TBL where id in (?) for update", id);
        jdbcTemplate.queryForList("select * from `seata_account_tbl` where id in (?) for update", id);
        jdbcTemplate.queryForList("select * from locals_production.seata_account_tbl where id in (?) for update", id);
        jdbcTemplate.queryForList("select * from locals_production.`seata_account_tbl` where id in (?) for update", id);
        throw new RuntimeException("查询锁失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-account-for-update-with-between")
    public void forUpdateWithBetween(int id) {
        jdbcTemplate.queryForList("select * from seata_account_tbl where id between ? and ? for update", id, id);
        jdbcTemplate.queryForList("select * from seata_account_TBL where id between ? and ? for update", id, id);
        jdbcTemplate.queryForList("select * from `seata_account_tbl` where id between ? and ? for update", id, id);
        jdbcTemplate.queryForList("select * from locals_production.seata_account_tbl where id between ? and ? for update", id, id);
        jdbcTemplate.queryForList("select * from locals_production.`seata_account_tbl` where id between ? and ? for update", id, id);
        throw new RuntimeException("查询锁失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit")
    public void debit(String userId, int money) {
        jdbcTemplate.update("update seata_account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update `seata_account_tbl` set money = money - ? where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update locals_production.seata_account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit-with-in")
    public void debitWithIn(String userId, int money) {
        jdbcTemplate.update("update seata_account_tbl set money = money - ? where user_id in (?)", new Object[] {money, userId});
        String sql = "update seata_account_tbl set money = money - " + money + " where user_id in ('" + userId + "')";
        jdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit-with-between")
    public void debitWithBetween(String userId, int money) {
        jdbcTemplate.update("update seata_account_tbl set money = money - ? where user_id between ? and ?", new Object[] {money, userId, userId});
        String sql = "update seata_account_tbl set money = money - " + money + " where user_id between '" + userId + "' and '" + userId + "'";
        jdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit-with-exist")
    public void debitWithExist(String userId, int money) {
        jdbcTemplate.update("update seata_account_tbl a set money = money - ? "
            + "where exists (select 1 from seata_order_tbl o where a.user_id = o.user_id and o.user_id = ?)", money, userId);
        String sql = "update seata_account_tbl a set money = money - " + money + " where exists (select 1 from seata_order_tbl o where a.user_id = o.user_id and o.user_id = '" + userId + "')";
        jdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit-with-not-exist")
    public void debitWithNotExist(String userId, int money) {
        jdbcTemplate.update("update seata_account_tbl a set money = money - ? "
            + "where not exists (select 1 from seata_order_tbl o where a.user_id = o.user_id and o.user_id = ?)", money, userId);
        String sql = "update seata_account_tbl a set money = money - " + money + " where not exists (select 1 from seata_order_tbl o where a.user_id = o.user_id and o.user_id = '" + userId + "')";
        jdbcTemplate.update(sql);
        throw new RuntimeException("扣除余额失败");
    }

    /**
     * 创建账户
     *
     * @param userId
     * @param money
     */
    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-create-account")
    public void createAccount(String userId, int money) {
        jdbcTemplate.update("insert into seata_account_tbl(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        //jdbcTemplate.update("insert into seata_account_TBL(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        //jdbcTemplate.update("insert into `seata_account_tbl`(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        //jdbcTemplate.update("insert into locals_production.seata_account_tbl(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        throw new RuntimeException("创建账户失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-create-account-with-pk")
    public void createAccountWithPk(int id, String userId, int money) {
        jdbcTemplate.update("insert into seata_account_tbl(id, user_id, money) values (?, ?, ?)", id, userId, money);
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account")
    public void deleteAccount(String userId) {
        jdbcTemplate.update("delete from seata_account_tbl where user_id = ?", userId);
        jdbcTemplate.update("delete from seata_account_TBL where user_id = ?", userId);
        jdbcTemplate.update("delete from `seata_account_tbl` where user_id = ?", userId);
        jdbcTemplate.update("delete from locals_production.seata_account_tbl where user_id = ?", userId);
        jdbcTemplate.update("delete from locals_production.`seata_account_tbl` where user_id = ?", userId);
        throw new RuntimeException("账户删除失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account-with-in")
    public void deleteAccountWithIn(String userId) {
        jdbcTemplate.update("delete from seata_account_tbl where user_id in (?)", userId);
        jdbcTemplate.update("delete from seata_account_TBL where user_id in (?)", userId);
        jdbcTemplate.update("delete from `seata_account_tbl` where user_id in (?)", userId);
        jdbcTemplate.update("delete from locals_production.seata_account_tbl where user_id in (?)", userId);
        jdbcTemplate.update("delete from locals_production.`seata_account_tbl` where user_id in (?)", userId);
        throw new RuntimeException("账户删除失败");
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account-with-between")
    public void deleteAccountWithBetween(int id) {
        jdbcTemplate.update("delete from seata_account_tbl where id between ? and ?", id, id);
        jdbcTemplate.update("delete from seata_account_TBL where id between ? and ?", id, id);
        jdbcTemplate.update("delete from `seata_account_tbl` where id between ? and ?", id, id);
        jdbcTemplate.update("delete from locals_production.seata_account_tbl where id between ? and ?", id, id);
        jdbcTemplate.update("delete from locals_production.`seata_account_tbl` where id between ? and ?", id, id);
        throw new RuntimeException("账户删除失败");
    }

    @Override
    public void updateAccountInformation(String userId, String information) {
        jdbcTemplate.update("update seata_account_tbl set information = ? where user_id = ?", new Object[]{information.getBytes(), userId});
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
