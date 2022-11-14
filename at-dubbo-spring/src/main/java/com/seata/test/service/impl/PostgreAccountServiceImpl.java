package com.seata.test.service.impl;

import com.seata.test.service.AccountService;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
  * @author will.zjw
  * @date 2019-10-25 14:15
  */
@Slf4j
public class PostgreAccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    @GlobalLock
    @Transactional
    public void forUpdate(int id, boolean shouldThrowException) {
        jdbcTemplate.queryForList("select * from account_tbl where id = ? for update", id);
        jdbcTemplate.queryForList("select * from \"account_tbl\" where id = ? for update", id);
        //in
        jdbcTemplate.queryForList("select * from account_tbl where id in (?) for update", id);
        //between
        jdbcTemplate.queryForList("select * from account_tbl where id between ? and ? for update", id, id);
        if (shouldThrowException) {
            throw new RuntimeException("查询锁失败");
        } else {
            log.info("-----------------------------查询锁成功-----------------------------");
        }
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit")
    public void debit(String userId, int money, boolean shouldThrowException) {
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update \"account_tbl\" set money = money - ? where user_id = ?", new Object[] {money, userId});
        //in
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id in (?)", new Object[] {money, userId});
        //between
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id between ? and ?", new Object[] {money, userId, userId});
        //batch
        jdbcTemplate.batchUpdate("update account_tbl set money = money - " + money + " where user_id = '" + userId + "'", "update account_tbl set money = money - " + money + " where user_id = '" + userId + "'");
        jdbcTemplate.batchUpdate("update account_tbl set money = money - ? where user_id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, money);
                ps.setString(2, userId);
            }
            @Override
            public int getBatchSize() {
                return 2;
            }
        });
        if (shouldThrowException) {
            throw new RuntimeException("扣除余额失败");
        } else {
            log.info("-----------------------------扣除余额成功-----------------------------");
        }
    }


    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-create-account")
    public void createAccount(String userId, int money, boolean shouldThrowException) {
        int primaryKey = new Random().nextInt(999999);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->  {
            PreparedStatement preparedStatement = con.prepareStatement("insert into account_tbl(id, user_id, money) values (nextval('\"seata\".account_tbl_id_seq'), ?, ?)");
            int i = 1;
            preparedStatement.setString(i++, userId);
            preparedStatement.setInt(i++, money);
            return preparedStatement;
        }, keyHolder);
        log.info("key holder size: {}", keyHolder.getKeyList().size());
        if (shouldThrowException) {
            jdbcTemplate.update("insert into account_tbl(id, user_id, money) values (9999, trim(both ?), ?)", userId, money);
        }

        jdbcTemplate.update("insert into account_tbl(id, user_id, money) values (?, trim(both ?), ?)", primaryKey++, userId, money);
        jdbcTemplate.update("insert into account_tbl(id, user_id, money) values (nextval('\"seata\".account_tbl_id_seq'), trim(both ?), ?)", userId, money);
        jdbcTemplate.update("insert into seata.account_tbl(user_id, money, id) values (trim(both ?), ?, nextval('\"seata\".account_tbl_id_seq'))", userId, money);
        jdbcTemplate.update("insert into account_tbl(id, user_id, money) values (default, ?, ?)", userId, money);
        //batch
        jdbcTemplate.update("insert into account_tbl(id, money, user_id) values (nextval('\"seata\".account_tbl_id_seq'), ?, ?), " +
                "(nextval('\"seata\".account_tbl_id_seq'), ?, ?)", money, userId, money, userId);
        jdbcTemplate.update("insert into account_tbl(id, money, user_id) values (default, ?, ?), (default, ?, ?)", money, userId, money, userId);
        jdbcTemplate.batchUpdate("insert into account_tbl(id, money, user_id) values (default, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String userId = "U100009";
                ps.setInt(1, money);
                ps.setString(2, userId);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });
        if (shouldThrowException) {
            throw new RuntimeException("创建账户失败");
        } else {
            log.info("-----------------------------创建账户成功-----------------------------");
        }
    }

    @Override
    public void createOrUpdateAccount(String userId, boolean shouldThrowException) {

    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account")
    public void deleteAccount(String userId, boolean shouldThrowException) {
        jdbcTemplate.update("delete from account_tbl where user_id = ?", userId);
        jdbcTemplate.update("delete from \"account_tbl\" where user_id = ?", userId);
        //in
        jdbcTemplate.update("delete from account_tbl where user_id in (?)", userId);
        //between
        jdbcTemplate.update("delete from account_tbl where user_id between ? and ?", userId, userId);
        if (shouldThrowException) {
            throw new RuntimeException("账户删除失败");
        } else {
            log.info("-----------------------------删除账户成功-----------------------------");
        }
    }

    @Override
    public void updateAccountInformation(String userId, String information) {

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
