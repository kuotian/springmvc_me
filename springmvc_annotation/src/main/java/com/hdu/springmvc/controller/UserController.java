package com.hdu.springmvc.controller;

import com.hdu.springmvc.annotation.Controller;
import com.hdu.springmvc.annotation.RequestMapping;
import com.hdu.springmvc.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//使用@Controller注解的类，就表示是一个Handler类
@Controller
public class UserController {
    // http请求：http://localhost/queryUser4?id=10&name=messi
    @RequestMapping("/queryUser4")
    @ResponseBody
    public Map<String, Object> queryUser(Integer id, String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    // http请求：http://localhost/queryUser4?id=10&name=messi
    @RequestMapping("/addUser4")
    @ResponseBody
    public String addUser() {
        return "addUser4";
    }
}