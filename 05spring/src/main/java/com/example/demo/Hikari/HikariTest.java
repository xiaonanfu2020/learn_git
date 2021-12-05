package com.example.demo.Hikari;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.demo.Student;

@Deprecated
public class HikariTest {

    @Autowired
    private MyHikariConfig config;

    private Connection connection = null;
    private Statement statement = null;

    private void getStatement()  {
        try {
            connection = config.dataSource().getConnection();
            statement = connection.createStatement();
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException exception) {
            System.out.println("Connection error!");
            exception.printStackTrace();
        }
    }
    private void close() throws SQLException {
        statement.close();
        connection.close();
        System.out.println("Connection close");
    }
    public boolean insert(String sql){
        boolean rs = false;
        try {
            rs = statement.execute(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rs;
    }
    public List<Student> query(String sql) {
        List<Student> students = new ArrayList<>();
        try {

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setPwd(rs.getInt("password"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("query error");
        }
        return students;
    }

    /**
     * test result
     * Connection successful!
     * Student(id=1, name=test1, pwd=1234)
     * Connection close
     * @param args
     */
    public static void main(String[] args) {
        try {
            HikariTest db = new HikariTest();
            db.getStatement();
            String sql1 = "insert into student(id,name,password) values(2,'test2',1234)";
            db.insert(sql1);
            String sql2 = "select * from student where id=2";
            List<Student> students = db.query(sql2);
            students.forEach(System.out::println);
            db.close();
        } catch (SQLException exception) {
            System.out.println("main error");
        }
    }


}
