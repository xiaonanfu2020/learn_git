package com.example.demo.models;

import lombok.Data;

/**
 * 订单
 * @author zhaobaozhi
 */
@Data
public class Order {

    private Long user_id;
    private Integer status;
    public Order(long userId, int status) {
        this.user_id = userId;
        this.status = status;
    }
}
