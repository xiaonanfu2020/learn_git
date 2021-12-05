package com.example.demo.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.Student;

/**
 * @author zhaobaozhi
 */
public class MysqlJdbc {
    String url = "jdbc:mysql://localhost:3306/testmysql";
    String user = "root";
    String pwd = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    private void getConnection()  {
        try {
            connection = DriverManager.getConnection(url,user,pwd);
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
        preparedStatement.close();
        connection.close();
        System.out.println("Connection close");
    }
    public boolean insert(String sql,Object... args){

        boolean rs = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i < args.length; ++i) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            rs = preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rs;
    }
    public Student query(String sql, Object... args) {
        Student student = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0;i < args.length; ++i) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
               student = new Student();
               student.setId(rs.getInt("id"));
               student.setName(rs.getString("name"));
               student.setPwd(rs.getInt("password"));
            }
            return student;
        } catch (SQLException e) {
            System.out.println("query error");
        }
        return student;
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
            MysqlJdbc db = new MysqlJdbc();
            db.getConnection();
            String sql1 = "insert into student(id,name,password) values(?,?,?)";
            db.insert(sql1,1,"test1",1234);
            String sql2 = "select * from student where id=?";
            Student student = db.query(sql2,1);
            System.out.println(student.toString());
            db.close();
        } catch (SQLException exception) {
            System.out.println("main error");
        }
    }

}
