package com.seata.service;

import com.seata.entity.Account;

/**
 * 账户
 * @Author will
 */
public interface AccountService {

    /**
     * 查询账户
     * @param id
     * @return
     */
    Account get(int id);

    /**
     * 查询锁
     * @param id
     */
    void forUpdate(int id);

    /**
     * 查询锁(in命令)
     * @param id
     */
    void forUpdateWithIn(int id);

    /**
     * 查询锁(between命令)
     * @param id
     */
    void forUpdateWithBetween(int id);

    /**
     * 余额扣款
     * @param userId 用户id
     * @param money 扣款金额
     */
    void debit(String userId, int money);

    /**
     * 余额扣除(in命令)
     * @param userId
     * @param money
     */
    void debitWithIn(String userId, int money);

    /**
     * 余额扣除(between命令)
     * @param userId
     * @param money
     */
    void debitWithBetween(String userId, int money);

    /**
     * 扣除余额(exist 命令)
     * @param userId
     * @param money
     */
    void debitWithExist(String userId, int money);

    /**
     * 扣除余额(not exist 命令)
     * @param userId
     * @param money
     */
    void debitWithNotExist(String userId, int money);

    /**
     * 创建账户
     * @param userId
     * @param money
     */
    void createAccount(String userId, int money);

    /**
     * 创建账户
     * @param id
     * @param userId
     * @param money
     */
    void createAccountWithPk(int id, String userId, int money);

    /**
     * 删除账户
     * @param userId
     */
    void deleteAccount(String userId);

    /**
     * 删除账户(in命令)
     * @param userId
     */
    void deleteAccountWithIn(String userId);

    /**
     * 删除账户(between命令)
     * @param id
     */
    void deleteAccountWithBetween(int id);
}
