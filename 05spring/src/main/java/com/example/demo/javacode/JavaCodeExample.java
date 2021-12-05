package com.example.demo.javacode;

import org.springframework.stereotype.Component;

/**
 * @author zhaobaozhi
 */
@Component
public class JavaCodeExample {
    public JavaCodeExample() {
        System.out.println("Construct Example");
    }

    public void info() {
        System.out.println("JavaCode Example");
    }
}
