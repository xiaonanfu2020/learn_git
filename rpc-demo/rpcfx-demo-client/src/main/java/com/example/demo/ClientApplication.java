package com.example.demo;

import com.example.demo.proxy.RpcClient;
import com.example.demo.proxy.RpcClientCglib;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientApplication {

    public static void main(String[] args) {

        RpcClient cglibClient = new RpcClientCglib();
        UserService userService = cglibClient.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        if (user == null) {
            log.info("Client service invoke error");
            return;
        }
        System.out.println("find user id=1 from server: " + user.getName());
        OrderService orderService = cglibClient.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findById(2);
        if (order == null) {
            log.info("Clint service invoke Error");
            return;
        }
        System.out.println(String.format("find order name=%s, user=%d",order.getName(),order.getUserId()));

    }

}
