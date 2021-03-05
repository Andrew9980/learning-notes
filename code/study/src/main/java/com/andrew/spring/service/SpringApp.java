package com.andrew.spring.service;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringApp {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext();
//        ApplicationContext context = new FileSystemXmlApplicationContext();
        ApplicationContext context = new AnnotationConfigApplicationContext();
//        ApplicationContext context = new AnnotationConfigWebApplicationContext();
    }

}
