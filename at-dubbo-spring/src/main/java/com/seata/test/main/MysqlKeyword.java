package com.seata.test.main;

import com.seata.test.ApplicationKeeper;
import com.seata.test.service.AccountService;
import com.seata.test.service.KeywordService;
import com.seata.test.service.impl.MysqlKeywordServiceImpl;
import com.seata.test.service.impl.OracleAccountServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * @author will.zjw
  * @date 2019-04-23 18:39
  */
public class MysqlKeyword {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-keyword-mysql-service.xml"});

        KeywordService keywordService = context.getBean("keywordService", MysqlKeywordServiceImpl.class);

        String name = "name";

        try {
            //保存(表名关键字)
            keywordService.insertWithKeywordTableName(name);
        } catch (Exception e){
            e.printStackTrace();
        }

        new ApplicationKeeper(context).keep();
    }
}
