package com.example.demo.auto;

import org.springframework.stereotype.Component;

/**
 * @author zhaobaozhi
 */
@Component
public class AutoWiredExample {
    public AutoWiredExample() {
        System.out.println("Construct Example");
    }

    public void info() {
        System.out.println("Auto wired example");
    }
}
