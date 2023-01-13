package com.seata.controller;

import com.seata.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/19
 */
@RequestMapping("/business")
@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PutMapping("/purchase")
    public String purchase(@RequestParam("userId") String userId,
                           @RequestParam("commodity_code") String commodity_code,
                           @RequestParam("count") Integer count,
                           @RequestParam("money") BigDecimal money) {
        businessService.purchase(userId, commodity_code, count, money);
        return "下单成功";
    }
}
