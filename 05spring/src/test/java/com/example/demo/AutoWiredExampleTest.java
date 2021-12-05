package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.auto.AutoWiredExample;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AutoWiredExample.class)
public class AutoWiredExampleTest {
    @Autowired
    private AutoWiredExample example;

    @Test
    public void Test() {
        example.info();
    }
}
