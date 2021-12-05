package com.example.demo.Hikari;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.Student;

import ch.qos.logback.core.encoder.EchoEncoder;

/**
 * @author zhaobaozhi
 */
@Component
public class HikariRunner implements CommandLineRunner {

    @Autowired
    private MyHikariConfig config;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select * from student";
        String sql1 = "insert into student(id,name,password) values(2,'test2',1234)";
        Connection connection = config.dataSource().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql1);
        ResultSet rs = statement.executeQuery(sql);
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student std = new Student();
            std.setId(rs.getInt("id"));
            std.setName(rs.getString("name"));
            std.setPwd(rs.getInt("password"));
            students.add(std);
        }
        students.forEach(System.out::println);


    }




}
