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
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
  * @author will.zjw
  * @date 2019-04-19 16:25
  */
@Slf4j
public class MysqlAccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    @GlobalLock
    @Transactional
    public void forUpdate(int id, boolean shouldThrowException) {
        jdbcTemplate.queryForList("select * from account_tbl where id = ? for update", id);
        jdbcTemplate.queryForList("select * from `account_tbl` where id = ? for update", id);
        jdbcTemplate.queryForList("select * from seata_client.account_tbl where id = ? for update", id);
        jdbcTemplate.queryForList("select * from seata_client.`account_tbl` where id = ? for update", id);
        //in
        jdbcTemplate.queryForList("select * from account_tbl where id in (?) for update", id);
        //between
        jdbcTemplate.queryForList("select * from account_tbl where id between ? and ? for update", id, id);
        //multi pk
        jdbcTemplate.queryForList("select * from account_tbl_multi_pk where id = ? for update", id);
        if (shouldThrowException) {
            throw new RuntimeException("查询锁失败");
        } else {
            log.info("-----------------------------查询锁成功-----------------------------");
        }
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-debit")
    public void debit(String userId, int money, boolean shouldThrowException) {
        jdbcTemplate.update("update account_tbl set money = money - ?, sex = 1 where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update `account_tbl` set money = money - ? where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update seata_client.account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update seata_client.`account_tbl` set money = money - ? where user_id = ?", new Object[] {money, userId});
        jdbcTemplate.update("update seata_client.`account_tbl` set money = money - ? where user_id in (?, ?)", new Object[] {money, "U100002", "U100003"});
        jdbcTemplate.update("update seata_client.`account_tbl` set money = money - ? where user_id in (?, ?, ?)", new Object[] {money, "U100002", "U100003", "U100004"});
        jdbcTemplate.update("update seata_client.`account_tbl` set money = money - ? where user_id in (?, ?)", new Object[] {money, "U100002", "U100004"});
        //in
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id in (?)", new Object[] {money, userId});
        jdbcTemplate.update("update account_tbl set money = money - \" + money + \" where user_id in ('\" + userId + \"')");
        //between
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id between ? and ?", new Object[] {money, userId, userId});
        jdbcTemplate.update("update account_tbl set money = money - " + money + " where user_id between '" + userId + "' and '" + userId + "'");
        //exist
        jdbcTemplate.update("update account_tbl a set money = money - ? "
                + "where exists (select 1 from order_tbl o where a.user_id = o.user_id and o.user_id = ?)", money, userId);
        jdbcTemplate.update("update account_tbl a set money = money - " + money + " where exists (select 1 from order_tbl o where a.user_id = o.user_id and o.user_id = '" + userId + "')");
        //not exist
        jdbcTemplate.update("update account_tbl a set money = money - ? "
                + "where not exists (select 1 from order_tbl o where a.user_id = o.user_id and o.user_id = ?)", money, userId);
        jdbcTemplate.update("update account_tbl a set money = money - " + money + " where not exists (select 1 from order_tbl o where a.user_id = o.user_id and o.user_id = '" + userId + "')");
        //multi pk
        jdbcTemplate.update("update account_tbl_multi_pk set money = money - ?, sex = 1 where user_id = ? or user_id = ?", new Object[] {money, userId, "U100003"});
        //batch(mysql8好像不支持)
//        jdbcTemplate.update("update seata_client_client.`account_tbl` set money = money - ? where user_id = ?;update seata_client.`account_tbl` set money = money - ? where user_id = ?;",
//                new Object[] {money, userId, money, userId});
        jdbcTemplate.batchUpdate(
                "update account_tbl set money = money - " + money + ", sex = 1 where user_id = \"" + userId + "\"",
                "update account_tbl set money = money - " + money + ", sex = 1 where user_id = \"" + userId + "\"");
        jdbcTemplate.batchUpdate("update account_tbl set money = money - ?, sex = 1 where user_id = ?", new BatchPreparedStatementSetter() {
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
        //batch with multi pk
        jdbcTemplate.batchUpdate("update account_tbl_multi_pk set money = money - ?, sex = 1 where user_id = ?", new BatchPreparedStatementSetter() {
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

    /**
     * 创建账户
     *
     * @param userId
     * @param money
     */
    @Override
    //@Transactional
    @GlobalTransactional(timeoutMills = 300000, name = "gts-create-account")
    public void createAccount(String userId, int money, boolean shouldThrowException) {
        int primaryKey = new Random().nextInt(999999);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->  {
            PreparedStatement preparedStatement = con.prepareStatement("insert ignore into account_tbl(user_id, money, information) values (?, ?, ?)");
            int i = 1;
            preparedStatement.setString(i++, userId);
            preparedStatement.setInt(i++, money);
            preparedStatement.setString(i++, "a");
            return preparedStatement;
        }, keyHolder);
        log.info("key holder size: {}", keyHolder.getKeyList().size());
        jdbcTemplate.update("replace into account_tbl(user_id, money, information, create_time) values (?, ?, ?, now())", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())", primaryKey++, userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl(USER_ID, money, information, create_time) values (?, ?, ?, now())", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl(create_time, money, information, user_id) values (now(), ?, ?, ?)", money, "hello world".getBytes(), userId);
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information) values (?, ?, ?, ?)", primaryKey, userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information) values (null, ?, ?, ?)", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into `account_tbl`(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into seata_client.account_tbl(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into seata_client.`account_tbl`(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into `seata`.`account_tbl`(user_id, money, information) values (?, ?, ?)", userId, money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl_multi_pk(user_id, sex, money) values (?, ?, ?)", userId, 1, money);
        jdbcTemplate.update("insert into account_tbl_multi_pk(USER_ID, sex, money) values (?, ?, ?)", userId, 1, money);
        //batch
        jdbcTemplate.update("insert into account_tbl(money, create_time, information, user_id) values (?, now(), ?, ?), (?, now(), ?, ?)", money, "hello world".getBytes(), primaryKey++, money, "hello world".getBytes(), primaryKey++);
        jdbcTemplate.update("insert into account_tbl(money, create_time, information, user_id, id) values (?, now(), ?, ?, ?), (?, now(), ?, ?, ?)", money, "hello world".getBytes(), "U100009", primaryKey++, money, "hello world".getBytes(), "U100009", primaryKey++);
        jdbcTemplate.update("insert into account_tbl(create_time, money, information, user_id) values (now(), ?, ?, ?), (now(), ?, ?, ?)", money, "hello world".getBytes(), "U100009", money, "hello world".getBytes(), "U100009");
        jdbcTemplate.update("insert into account_tbl(create_time, money, information, user_id, id) values (now(), ?, ?, ?, ?), (now(), ?, ?, ?, ?)", money, "hello world".getBytes(), "U100009", primaryKey++, money, "hello world".getBytes(), "U100009", primaryKey++);
        jdbcTemplate.batchUpdate("insert into account_tbl(create_time, money, information, user_id) values (now(), ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String userId = "U100009";
                ps.setInt(1, money);
                ps.setString(2, userId);
                ps.setBytes(3, "hello world".getBytes());
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
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account")
    public void deleteAccount(String userId, boolean shouldThrowException) {
        jdbcTemplate.update("delete from account_tbl where user_id = ?", userId);
        jdbcTemplate.update("delete from `account_tbl` where user_id = ?", userId);
        jdbcTemplate.update("delete from seata_client.account_tbl where user_id = ?", userId);
        jdbcTemplate.update("delete from seata_client.`account_tbl` where user_id = ?", userId);
        //order by
        jdbcTemplate.update("delete from account_tbl where user_id = ? order by user_id", userId);
        jdbcTemplate.update("delete from account_tbl where user_id = ? order by user_id desc", userId);
        jdbcTemplate.update("delete from account_tbl where user_id = ? order by user_id asc", userId);
        //limit
        jdbcTemplate.update("delete from account_tbl where user_id = ? limit 1", userId);
        //order by + limit
        jdbcTemplate.update("delete from account_tbl where user_id = ? order by user_id limit 1", userId);
        jdbcTemplate.update("delete from account_tbl where user_id = ? order by user_id desc limit 1", userId);
        //in
        jdbcTemplate.update("delete from account_tbl where user_id in (?)", userId);
        //between
        jdbcTemplate.update("delete from account_tbl where user_id between ? and ?", userId, userId);
        //multi pk
        jdbcTemplate.update("delete from account_tbl_multi_pk where user_id = ?", userId);
        if (shouldThrowException) {
            throw new RuntimeException("账户删除失败");
        } else {
            log.info("-----------------------------删除账户成功-----------------------------");
        }
    }

    @Override
    public void updateAccountInformation(String userId, String information) {
        jdbcTemplate.update("update account_tbl set information = ? where user_id = ?", new Object[]{information.getBytes(), userId});
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
