package com.seata.test.service;

import com.seata.test.entity.Order;

public interface OrderService {

    /**
     * 插入订单
     * @param userId
     * @param commodityCode
     * @param orderCount
     * @param money
     */
    void insertOrder(String userId, String commodityCode, int orderCount, int money);

    /**
     * 创建订单
     * @param userId        用户id
     * @param commodityCode 商品编号
     * @param orderCount    订购数量
     * @return  生成的订单order
     */
    Order create(String userId, String commodityCode, int orderCount);
}
