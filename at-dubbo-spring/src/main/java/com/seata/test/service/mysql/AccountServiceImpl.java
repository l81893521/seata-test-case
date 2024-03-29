package com.seata.test.service.mysql;


import com.alibaba.nacos.common.utils.UuidUtils;
import com.google.common.collect.Lists;
import com.seata.test.service.AccountService;
import com.seata.test.util.UUIDGenerator;
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

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

/**
  * @author will.zjw
  * @date 2019-04-19 16:25
  */
@Slf4j
public class AccountServiceImpl implements AccountService {

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
        jdbcTemplate.update("update account_tbl set money = money - ?, sex = 1 where user_id = ?", money, userId);
        jdbcTemplate.update("update `account_tbl` set money = money - ? where user_id = ?", money, userId);
        jdbcTemplate.update("update seata_client.account_tbl set money = money - ? where user_id = ?", money, userId);
        jdbcTemplate.update("update seata_client.`account_tbl` set money = money - ? where user_id = ?", money, userId);
        //in
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id in (?)", money, userId);
        //between
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id between ? and ?", money, userId, userId);
        //exist
        jdbcTemplate.update("update account_tbl a set money = money - ? "
                + "where exists (select 'U100002' as user_id from dual)", money);

        //not exist
        jdbcTemplate.update("update account_tbl a set money = money - ? "
                + "where not exists (select 'U100003' as user_id from dual)", money);

        //multi pk
        jdbcTemplate.update("update account_tbl_multi_pk set money = money - ?, sex = 1 where user_id = ?", money, userId);

        //batch(mysql8好像不支持)
//        jdbcTemplate.batchUpdate("update account_tbl set money = money - ? where user_id = ?; " +
//                        "update account_tbl set money = money - ? where user_id = ?;",
//                Lists.newArrayList(new Object[]{money, userId}, new Object[]{money, userId}));
//        jdbcTemplate.batchUpdate(
//                "update account_tbl set money = money - " + money + ", sex = 1 where user_id = \"" + userId + "\"",
//                "update account_tbl set money = money - " + money + ", sex = 1 where user_id = \"" + userId + "\"");
//        jdbcTemplate.batchUpdate("update account_tbl set money = money - ?, sex = 1 where user_id = ?", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setInt(1, money);
//                ps.setString(2, userId);
//            }
//            @Override
//            public int getBatchSize() {
//                return 2;
//            }
//        });
        //batch with multi pk
//        jdbcTemplate.batchUpdate("update account_tbl_multi_pk set money = money - ?, sex = 1 where user_id = ?", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setInt(1, money);
//                ps.setString(2, userId);
//            }
//
//            @Override
//            public int getBatchSize() {
//                return 2;
//            }
//        });
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
    @GlobalTransactional(timeoutMills = 300000, name = "gts-create-account")
    public void createAccount(String userId, int money, boolean shouldThrowException) {

//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(con ->  {
//            PreparedStatement preparedStatement = con.prepareStatement("insert ignore into account_tbl(user_id, money, information) values (?, ?, ?)");
//            int i = 1;
//            preparedStatement.setString(i++, userId);
//            preparedStatement.setInt(i++, money);
//            preparedStatement.setString(i++, "a");
//            return preparedStatement;
//        }, keyHolder);
//        log.info("key holder size: {}", keyHolder.getKeyList().size());
//        jdbcTemplate.update("replace into account_tbl(user_id, money, information, create_time) values (?, ?, ?, now())", userId, money, "hello world".getBytes());
        // auto increment
        jdbcTemplate.update("insert into account_tbl(USER_ID, money, information, create_time) values (?, ?, ?, now())", UUID.randomUUID().toString(), money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl(create_time, money, information, user_id) values (now(), ?, ?, ?)", money, "hello world".getBytes(), UUID.randomUUID().toString());
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information) values (null, ?, ?, ?)", UUID.randomUUID().toString(), money, "hello world".getBytes());
        jdbcTemplate.update("insert into `account_tbl`(user_id, money, information) values (?, ?, ?)", UUID.randomUUID().toString(), money, "hello world".getBytes());
        jdbcTemplate.update("insert into seata_client.account_tbl(user_id, money, information) values (?, ?, ?)", UUID.randomUUID().toString(), money, "hello world".getBytes());
        jdbcTemplate.update("insert into seata_client.`account_tbl`(user_id, money, information) values (?, ?, ?)", UUID.randomUUID().toString(), money, "hello world".getBytes());
        jdbcTemplate.update("insert into `seata_client`.`account_tbl`(user_id, money, information) values (?, ?, ?)", UUID.randomUUID().toString(), money, "hello world".getBytes());
        // insert primary key
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())", UUIDGenerator.generateUUID(), UUID.randomUUID().toString(), money, "hello world".getBytes());
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information) values (?, ?, ?, ?)", UUIDGenerator.generateUUID(), UUID.randomUUID().toString(), money, "hello world".getBytes());
        // multi pk
        jdbcTemplate.update("insert into account_tbl_multi_pk(user_id, sex, money) values (?, ?, ?)", UUID.randomUUID().toString(), 1, money);
        jdbcTemplate.update("insert into account_tbl_multi_pk(USER_ID, sex, money) values (?, ?, ?)", UUID.randomUUID().toString(), 1, money);
        //batch
        jdbcTemplate.update("insert into account_tbl(money, create_time, information, user_id) values (?, now(), ?, ?), (?, now(), ?, ?)",
                money, "hello world".getBytes(), UUID.randomUUID().toString(),
                money, "hello world".getBytes(), UUID.randomUUID().toString());
        jdbcTemplate.update("insert into account_tbl(money, create_time, information, user_id, id) values (?, now(), ?, ?, ?), (?, now(), ?, ?, ?)",
                money, "hello world".getBytes(), UUID.randomUUID().toString(), UUIDGenerator.generateUUID(),
                money, "hello world".getBytes(), UUID.randomUUID().toString(), UUIDGenerator.generateUUID());
        jdbcTemplate.update("insert into account_tbl(create_time, money, information, user_id) values (now(), ?, ?, ?), (now(), ?, ?, ?)",
                money, "hello world".getBytes(), UUID.randomUUID().toString(),
                money, "hello world".getBytes(), UUID.randomUUID().toString());
        jdbcTemplate.update("insert into account_tbl(create_time, money, information, user_id, id) values (now(), ?, ?, ?, ?), (now(), ?, ?, ?, ?)",
                money, "hello world".getBytes(), UUID.randomUUID().toString(), UUIDGenerator.generateUUID(),
                money, "hello world".getBytes(), UUID.randomUUID().toString(), UUIDGenerator.generateUUID());
        jdbcTemplate.batchUpdate("insert into account_tbl(create_time, money, information, user_id) values (now(), ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, money);
                ps.setBytes(2, "hello world".getBytes());
                ps.setString(3, UUID.randomUUID().toString());
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
    @GlobalTransactional(timeoutMills = 300000)
    public void createOrUpdateAccount(String userId, boolean shouldThrowException) {

        /**
         * single
         */
        //normal
//        jdbcTemplate.update("insert into account_tbl(user_id, money, information, create_time) values (?, ?, ?, now())" +
//                " on duplicate key update money = 1000", userId, 1000, "hello world".getBytes(StandardCharsets.UTF_8));
        //insert null
//        jdbcTemplate.update("insert into account_tbl(user_id, money, information, create_time) values (null, ?, ?, now())" +
//                " on duplicate key update money = 1000", 1000, "hello world".getBytes(StandardCharsets.UTF_8));

        /**
         * batch
         */
        //normal
//        jdbcTemplate.update("insert into account_tbl(user_id, money, information, create_time) " +
//                "values (?, ?, ?, now()), (?, ?, ?, now())" +
//                " on duplicate key update money = 1000",
//                userId, 1000, "hello world".getBytes(StandardCharsets.UTF_8),
//                userId, 1000, "hello world".getBytes(StandardCharsets.UTF_8));

        //insert null
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) " +
                "values (?, ?, ?, ?, now()), (null, ?, ?, ?, now()) " +
                "on duplicate key update money = 1000",
                UUIDGenerator.generateUUID(), userId, 1000, "hello world".getBytes(StandardCharsets.UTF_8),
                userId, 1000, "hello wolrd".getBytes(StandardCharsets.UTF_8));


        if (shouldThrowException) {
            throw new RuntimeException("create or update failed");
        } else {
            log.info("create or update successed");
        }
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-delete-account")
    public void deleteAccount(String userId, boolean shouldThrowException) {
        Long id = UUIDGenerator.generateUUID();
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ?", id);

        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from `account_tbl` where id = ?", id);

        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from seata_client.account_tbl where id = ?", id);

        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from seata_client.`account_tbl` where id = ?", id);

        //order by
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ? order by user_id", id);

        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ? order by user_id desc", id);

        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ? order by user_id asc", id);

        //limit
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ? limit 1", id);

        //order by + limit
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ? order by user_id limit 1", id);

        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id = ? order by user_id desc limit 1", id);

        //in
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id in (?)", id);

        //between
        jdbcTemplate.update("insert into account_tbl(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl where id between ? and ?", id, id);

        //multi pk
        jdbcTemplate.update("insert into account_tbl_multi_pk(id, user_id, money, information, create_time) values (?, ?, ?, ?, now())",
                id, UUID.randomUUID().toString(), 999, "hello world".getBytes());
        jdbcTemplate.update("delete from account_tbl_multi_pk where id = ?", id);
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
