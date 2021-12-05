package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.demo.javacode.JavaCodeCofig;
import com.example.demo.javacode.JavaCodeExample;

public class JavaCodeExampleTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaCodeCofig.class);
        JavaCodeExample example = context.getBean(JavaCodeExample.class);
        example.info();
    }
}
