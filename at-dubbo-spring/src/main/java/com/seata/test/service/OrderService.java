package com.seata.test.service;

import com.seata.test.entity.Order;

public interface OrderService {

    /**
     * 分别正常执行
     * 插入->更新->删除
     */
    void testSeataForSuccess();

    /**
     * 分别执行
     * 插入->更新->删除
     * 然后抛出异常
     */
    void testSeataForFail();

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
