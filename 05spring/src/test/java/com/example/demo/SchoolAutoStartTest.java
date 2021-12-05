package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.school.start.MySchool;
import com.school.start.SchoolAutoConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SchoolAutoConfiguration.class)
public class SchoolAutoStartTest {

    @Autowired
    MySchool mySchool;

    @Test
    public void test() {
        System.out.println(mySchool.toString());
    }
}
