//package com.seata.config;
//
//import io.seata.spring.annotation.GlobalTransactionScanner;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
//  * @author will.zjw
//  * @date 2019-12-24 16:17
//  *            佛主保佑,不要出BUG
//  *                 _ooOoo_
//  *                o8888888o
//  *                88" . "88
//  *                (| -_- |)
//  *                O\  =  /O
//  *             ____/`---'\____
//  *           .'  \\|     |//  `.
//  *          /  \\|||  :  |||//  \
//  *         /  _||||| -:- |||||-  \
//  *         |   | \\\  -  /// |   |
//  *         | \_|  ''\---/''  |   |
//  *          \  .-\__  `-`  ___/-. /
//  *        ___`. .'  /--.--\  `. . __
//  *      ."" '<  `.___\_<|>_/___.'  >'"".
//  *    | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//  *    \  \ `-.   \_ __\ /__ _/   .-` /  /
//  * ======`-.____`-.___\_____/___.-`____.-'======
//  *                 `=---='
//  */
//@Configuration
//public class SeataGlobalTransactionScannerConfig {
//
//    @Value("${spring.cloud.alibaba.seata.tx-service-group}")
//    private String txServiceGroup;
//
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    @Bean
//    public GlobalTransactionScanner globalTransactionScanner() {
//        return new GlobalTransactionScanner(applicationName, txServiceGroup);
//    }
//}
