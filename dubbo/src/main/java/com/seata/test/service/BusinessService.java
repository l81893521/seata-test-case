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
     * 创建账户
     * @param userId
     * @param money
     */
    void createAccount(String userId, int money);
}
