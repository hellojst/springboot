package com.stjia.springboot.controller;

import com.stjia.springboot.domain.BookBean;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @RestController的意思就是controller里面的方法都以json格式输出，不用再写什么jackjson配置的了！
 * @author stjia
 * @date 2018/9/21 11:00
 */
@RestController
public class HelloWordControler {

    @Autowired
    private BookBean bookBean;

    private Logger logger = LoggerFactory.getLogger(HelloWordControler.class);

    //启动主程序，打开浏览器访问http://localhost:8080/hello，就可以看到效果了
    @RequestMapping("/hello")
    public String index(){
        logger.info("获取 hello world 时间：" + new Date(System.currentTimeMillis()));
        return "hello world";
    }

    @RequestMapping("/book")
    public String getbook(){
        String str = "Hello spring, the  book name is" + bookBean.getName() + ". the author is " + bookBean.getAuthor() + " and the price is " + bookBean.getPrice();
        return str;
    }

}
