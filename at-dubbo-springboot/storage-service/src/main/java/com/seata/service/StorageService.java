package com.seata.service;

import org.apache.dubbo.config.annotation.Service;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/18
 */
public interface StorageService {

    /**
     * 减少库存
     * @param commodityCode
     * @param count
     */
    void decrease(String commodityCode, int count);
}
