package com.seata.service.impl;

import com.seata.dao.StorageMapper;
import com.seata.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/18
 */
@Service(version = "1.0.0")
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    @GlobalTransactional(timeoutMills = 30000, name = "gts-decrease-storage")
    public void decrease(String commodityCode, int count) {
        storageMapper.decrease(commodityCode, count);
    }
}
