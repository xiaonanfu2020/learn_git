package com.example.demo.javacode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaobaozhi
 */
@Configuration
public class JavaCodeCofig {
    @Bean
    public JavaCodeExample getJavaCodeExample() {
        return new JavaCodeExample();
    }

}
