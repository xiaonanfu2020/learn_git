package com.example.demo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mappers.OrderMapper;
import com.example.demo.models.Order;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@MapperScan("com.example.demo.mappers")
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test @Transactional
    public void test() throws SQLException {
        // 通过sharding插入数据，通过sharding自己的日志输出看出插入不同的数据库和表
        orderMapper.insert(new Order(1L, 1));
        orderMapper.insert(new Order(2L, 1));


        Map<String, Object> condition = new HashMap<>(1);
        condition.put("user_id", 1L);

        List<Map<String, Object>> orderQuery = orderMapper.query(condition);
        assert orderQuery.size() == 1;
        for (Map item: orderQuery) {
            System.out.println(item.toString());
        }

        //查询
        condition = new HashMap<>(1);
        condition.put("user_id", 1L);
        orderQuery = orderMapper.query(condition);
        assert orderQuery.size() == 1;
        for (Map item: orderQuery) {
            System.out.println(item.toString());
        }

        // user_id，看到进行单库单表的查询
        condition = new HashMap<>(2);
        condition.put("user_id", 2L);
        orderQuery = orderMapper.query(condition);
        assert orderQuery.size() == 1;
        for (Map item: orderQuery) {
            System.out.println(item.toString());
        }
    }
}
