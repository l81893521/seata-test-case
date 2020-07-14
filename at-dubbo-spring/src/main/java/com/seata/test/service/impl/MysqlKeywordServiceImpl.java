package com.seata.test.service.impl;

import com.seata.test.service.KeywordService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
  * @author will.zjw
  * @date 2019-10-14 19:52
  */
public class MysqlKeywordServiceImpl implements KeywordService {

    private static final Logger logger = LoggerFactory.getLogger(KeywordService.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-insert-keyword-table-name")
    public void insertWithKeywordTableName(String name) {
        jdbcTemplate.update("insert into `table`(name) values (?)", new Object[] {name});
        jdbcTemplate.update("insert into seatA.`table`(name) values (?)", new Object[] {name});
        throw new RuntimeException("保存失败(关键字表名)");
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
