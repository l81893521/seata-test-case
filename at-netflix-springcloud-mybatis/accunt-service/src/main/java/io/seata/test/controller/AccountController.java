package io.seata.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiawei.zhang
 * @date: 2020/4/13
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/get")
    public String get() {
        return "success";
    }
}
