package com.seata;

import com.alibaba.druid.pool.DruidDataSource;
import com.seata.config.SqlSessionFactoryConfig;
import com.seata.controller.MysqlAccountController;
import com.seata.controller.OrderController;
import com.seata.controller.StorageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
  * @author will.zjw
  * @date 2019-12-16 17:13
  */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.seata", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {SqlSessionFactoryConfig.class, StorageController.class, MysqlAccountController.class, OrderController.class})})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
