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
    int insertOrder(String userId, String commodityCode, int orderCount, int money);

    /**
     * 更新订单
     * @param orderId
     */
    void updateOrder(int orderId, int money);

    /**
     * 更新订单状态
     * @param orderId
     * @param userId
     */
    void updateJoinOrderStatus(int orderId, String userId, boolean shouldThrowException);

    /**
     * 删除订单
     * @param orderId
     */
    void deleteOrder(int orderId);

    /**
     * 创建订单
     * @param userId        用户id
     * @param commodityCode 商品编号
     * @param orderCount    订购数量
     * @return  生成的订单order
     */
    Order create(String userId, String commodityCode, int orderCount);
}
