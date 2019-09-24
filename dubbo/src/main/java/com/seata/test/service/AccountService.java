package com.seata.test.service;

import io.seata.spring.annotation.GlobalTransactional;

/**
 * 账户
 * @Author will
 */
public interface AccountService {

    @GlobalTransactional(timeoutMills = 300000)
    void testX();

    /**
     * 余额扣款
     * @param userId 用户id
     * @param money 扣款金额
     */
    void debit(String userId, int money);

    /**
     * 余额扣款(oracle)
     * @param userId
     * @param money
     */
    void debitForOracle(String userId, int money);

    /**
     * platform数据源扣除余额
     * @param userId
     * @param money
     */
    void platformDebit(String userId, int money);

    /**
     * 创建账户
     * @param userId
     * @param money
     */
    void createAcount(String userId, int money);

    /**
     * 创建账户
     * @param id
     * @param userId
     * @param money
     */
    void createAccountWithPk(int id, String userId, int money);

    /**
     * 创建账户(oracle)
     * @param userId
     * @param money
     */
    void createAccountForOracle(String userId, int money);

    /**
     * 删除账户
     * @param userId
     */
    void deleteAccount(String userId);

    /**
     * 删除账户 (in命令)
     * @param userId
     */
    void deleteAccountForOracleWithIn(String userId);

    /**
     * 删除账户 (between命令)
     * @param id
     */
    void deleteAccountForOracleWithBetween(int id);

    /**
     * 删除账户 (like命令)
     * @param id
     */
    void deleteAccountForOracleWithLike(int id);

    /**
     * 修改账户信息
     * @param information
     */
    void updateAccountInformation(String userId, String information);

}
