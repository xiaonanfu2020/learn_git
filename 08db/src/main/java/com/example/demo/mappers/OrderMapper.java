package com.example.demo.mappers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Order;

/**
 * @author zhaobaozhi
 */
@Repository
public interface OrderMapper {

    void insert(Order order);
    void delete(Long userId);
    void update(Order order);
    List<Map<String, Object>> query(Map<String, Object> condition);
}
