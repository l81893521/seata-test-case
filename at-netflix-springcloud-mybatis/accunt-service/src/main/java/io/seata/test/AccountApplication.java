package io.seata.test;

import com.alibaba.cloud.seata.GlobalTransactionAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: jiawei.zhang
 * @date: 2020/4/13
 */
@SpringBootApplication(exclude = {GlobalTransactionAutoConfiguration.class})
@EnableEurekaClient
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
