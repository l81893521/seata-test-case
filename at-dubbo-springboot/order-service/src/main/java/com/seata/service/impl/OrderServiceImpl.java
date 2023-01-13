package com.seata.service.impl;

import com.seata.dao.OrderMapper;
import com.seata.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/19
 */
@Service(version = "1.0.0")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public void order(String userId, String commodity_code, int count, BigDecimal money) {
        orderMapper.save(userId, commodity_code, count, money);
    }
}
