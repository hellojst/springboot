package com.stjia.springboot.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author stjia
 * @date 2018/9/22 15:27
 */
@Component  //spring boot中ConfigurationProperties取消了location
@ConfigurationProperties(prefix = "book")
@PropertySource(value = "classpath:/book.properties")
public class BookBean {

    private String name;

    private String author;

    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
