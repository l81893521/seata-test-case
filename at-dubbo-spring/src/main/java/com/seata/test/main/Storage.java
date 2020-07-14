package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
  * @author will.zjw
  * @date 2019-04-24 14:30
  */
public class Storage {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext storageContext = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-storage-service.xml"});
        storageContext.getBean("storageService");
        JdbcTemplate storageJdbcTemplate = (JdbcTemplate) storageContext.getBean("jdbcTemplate");
        storageJdbcTemplate.update("delete from seata_storage_tbl where commodity_code = 'C00321'");
        storageJdbcTemplate.update("insert into seata_storage_tbl(commodity_code, count) values ('C00321', 100)");
        new ApplicationKeeper(storageContext).keep();
    }
}
