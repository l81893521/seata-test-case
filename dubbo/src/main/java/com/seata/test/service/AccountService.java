package com.seata.test.service;

/**
 * 账户
 */
public interface AccountService {

    /**
     * 余额扣款
     * @param userId 用户id
     * @param money 扣款金额
     */
    void debit(String userId, int money);

    /**
     * 创建账户
     * @param userId
     * @param money
     */
    void createAcount(String userId, int money);

    /**
     * 删除账户
     * @param userId
     */
    void deleteAccount(String userId);

    /**
     * 修改账户信息
     * @param information
     */
    void updateAccountInformation(String userId, String information);
}
