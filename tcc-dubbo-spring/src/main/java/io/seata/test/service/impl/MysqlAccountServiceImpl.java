package io.seata.test.service.impl;


import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.test.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
  * @author will.zjw
  * @date 2019-04-19 16:25
  */
public class MysqlAccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void debit(BusinessActionContext actionContext, String userId, int money) {
        jdbcTemplate.update("update account_tbl set freezing_money = freezing_money + ? where user_id = ?", new Object[] {money, userId});
    }

    @Override
    public boolean commitDebit(BusinessActionContext actionContext) {
        //幂等
        String userId = (String) actionContext.getActionContext("userId");
        int money = (int) actionContext.getActionContext("money");
        jdbcTemplate.update("update account_tbl set money = money - ?, freezing_money = freezing_money - ? where user_id = ?", new Object[] {money, money, userId});
        return true;
    }

    @Override
    public boolean rollbackDebit(BusinessActionContext actionContext) {
        //幂等
        String userId = (String) actionContext.getActionContext("userId");
        int money = (int) actionContext.getActionContext("money");
        jdbcTemplate.update("update account_tbl set freezing_money = freezing_money - ? where user_id = ?", new Object[] {money, userId});
        return true;
    }
}
