package com.seata.service;

import java.math.BigDecimal;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/19
 */
public interface OrderService {

    /**
     * 下单
     * @param userId
     * @param commodity_code
     * @param count
     * @param money
     */
    void order(String userId, String commodity_code, int count, BigDecimal money);
}
