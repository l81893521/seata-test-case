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
}
