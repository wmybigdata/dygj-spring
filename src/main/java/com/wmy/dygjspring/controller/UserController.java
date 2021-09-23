package com.wmy.dygjspring.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmy.dygjspring.bean.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author hao
 * @date 2021-09-23 09:47
 */
@RestController
public class UserController {
    //处理客户端的请求，并且进行响应
    @RequestMapping("/first")
    public String test() {
        return "this is first";
    }

    @RequestMapping("/second")
    public String test2() {
        return "this is second";
    }

    @PostMapping("/applog")
    public String logger(@RequestBody User user) {
        System.out.println(user);
        return user.toString();
    }

}
