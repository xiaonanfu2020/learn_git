package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.demo.XML.XMLExample;


public class XMLExampleTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("BeanConfig.xml");
        XMLExample example =   (XMLExample) context.getBean("xmlExample");
        example.info();
    }
}
