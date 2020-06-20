package com.seata.controller;

import com.seata.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/18
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PutMapping("/decrease")
    public void debit(String commodityCode, int count){
        storageService.decrease(commodityCode, count);
    }
}
