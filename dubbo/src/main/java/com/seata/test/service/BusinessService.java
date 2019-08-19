package com.seata.test.service;

public interface BusinessService {

    /**
     * 用户订购商品
     * @param userId 用户id
     * @param commodityCode 商品编号
     * @param orderCount 订购数量
     */
    void purchase(String userId, String commodityCode, int orderCount);

    /**
     * 扣除账户余额
     * @param userId
     * @param money
     */
    void debit(String userId, int money);

    /**
     * 扣除不同数据源的余额
     * @param userId
     * @param money
     */
    void debitByDiffentDataSource(String userId, int money);

    /**
     * 创建账户
     * @param userId
     * @param money
     */
    void createAccount(String userId, int money);

    /**
     * 删除账户
     * @param userId
     */
    void deleteAccount(String userId);

    /**
     * 修改账户信息
     * @param userId
     * @param information
     */
    void updateAccountInformation(String userId, String information);
}
