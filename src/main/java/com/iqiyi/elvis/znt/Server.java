package com.iqiyi.elvis.znt;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 10:47
 * Desc TODO
 */
public class Server {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
    }
}
