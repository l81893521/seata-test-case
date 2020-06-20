package com.seata.service;

import java.math.BigDecimal;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/19
 */
public interface BusinessService {

    /**
     * 购买
     */
    void purchase(String userId, String commodity_code, int count, BigDecimal money);
}
