package com.example.demo.Hikari;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author zhaobaozhi
 */
@Configuration
public class MyHikariConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        com.zaxxer.hikari.HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
