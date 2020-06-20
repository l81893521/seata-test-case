package com.seata.service.impl;

import com.seata.service.AccountService;
import com.seata.service.BusinessService;
import com.seata.service.OrderService;
import com.seata.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/19
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:28000")
    private AccountService accountService;

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:28001")
    private StorageService storageService;

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:28002")
    private OrderService orderService;

    @Override
    public void purchase(String userId, String commodity_code, int count, BigDecimal money) {
        accountService.debit(userId, money.intValue());
        storageService.decrease(commodity_code, count);
        orderService.order(userId, commodity_code, count, money);
    }
}
