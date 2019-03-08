package com.xiongya.annnotation.demo.controller;


import com.alibaba.fastjson.JSON;
import com.xiongya.annnotation.demo.anos.IgnoreToken;
import com.xiongya.annnotation.demo.anos.Log;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;



@RestController
public class LoginController {

    public static Map<String, String> map = new HashMap<String, String>();
    public static List<String> logList = new ArrayList<String>();
    public static Set<String> tokenSet = new HashSet<String>();

    @RequestMapping(value = "login")
    //@IgnoreToken
    public void login(@RequestBody String userName, String password){
        map.put(userName,password);
        //保存token
        tokenSet.add(userName+password);
        //返回token
        System.out.println(userName+password);
        //return userName+password;
    }

    @RequestMapping(value = "query")
    @Log(name = "获取密码")
    public String getPassword(String userName){
        //获取用户密码
        return map.get(userName);
    }

    @RequestMapping(value = "logs")
    @Log(name = "获取日志信息")
    @IgnoreToken
    public String getLogMap() {
        System.out.println("xiong");
        return JSON.toJSONString(logList);
    }


    @IgnoreToken  //因为aop会拦截这个类中的所有方法
    public boolean chkToken(String token){
        return tokenSet.contains(token);
    }

    @IgnoreToken
    public void saveLog (String log){
        logList.add(log);
    }

}
