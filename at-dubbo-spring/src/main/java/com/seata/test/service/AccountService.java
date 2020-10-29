package com.seata.test.service;

/**
 * 账户
 * @Author will
 */
public interface AccountService {

    /**
     * 查询锁
     * @param id
     */
    void forUpdate(int id, boolean shouldThrowException);

    /**
     * 余额扣款
     * @param userId 用户id
     * @param money 扣款金额
     */
    void debit(String userId, int money, boolean shouldThrowException);

    /**
     * 创建账户
     * @param userId
     * @param money
     */
    void createAccount(String userId, int money, boolean shouldThrowException);

    /**
     * 删除账户
     * @param userId
     */
    void deleteAccount(String userId, boolean shouldThrowException);

    /**
     * 修改账户信息
     * @param information
     */
    void updateAccountInformation(String userId, String information);

}
