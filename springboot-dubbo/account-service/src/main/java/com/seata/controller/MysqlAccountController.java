package com.seata.controller;

import com.seata.entity.Account;
import com.seata.service.AccountService;
import com.seata.service.impl.MysqlAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
  * @author will.zjw
  * @date 2019-12-24 14:13
  *            佛主保佑,不要出BUG
  *                 _ooOoo_
  *                o8888888o
  *                88" . "88
  *                (| -_- |)
  *                O\  =  /O
  *             ____/`---'\____
  *           .'  \\|     |//  `.
  *          /  \\|||  :  |||//  \
  *         /  _||||| -:- |||||-  \
  *         |   | \\\  -  /// |   |
  *         | \_|  ''\---/''  |   |
  *          \  .-\__  `-`  ___/-. /
  *        ___`. .'  /--.--\  `. . __
  *      ."" '<  `.___\_<|>_/___.'  >'"".
  *    | | :  `- \`.;`\ _ /`;.`/ - ` : | |
  *    \  \ `-.   \_ __\ /__ _/   .-` /  /
  * ======`-.____`-.___\_____/___.-`____.-'======
  *                 `=---='
  */
@RestController
@RequestMapping("/mysql")
public class MysqlAccountController {

    @Autowired
    @Qualifier("mysqlAccountServiceImpl")
    private AccountService accountService;

    @GetMapping("/account")
    public Account getAccount(int id) {
        return accountService.get(id);
    }

    @GetMapping("/account/forUpdate")
    public void forUpdate(int id) {
        accountService.forUpdate(id);
    }

    @PutMapping("/account/debit")
    public void debit(String userId, int money){
        accountService.debit(userId, money);
    }
}
