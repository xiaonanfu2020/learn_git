package com.example.demo.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.example.demo.api.RpcfxRequest;
import com.example.demo.api.RpcfxResponse;
import com.example.demo.netty.client.RpcNettyClientSync;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

@Slf4j
public class RpcInvocationHandler implements InvocationHandler, MethodInterceptor {

    private final Class<?> serviceClass;
    private final String url;

    <T> RpcInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return process(serviceClass, method, args, url);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {
        return process(serviceClass, method, args, url);
    }

    /**
     * 发送请求到服务端
     * 获取结果后序列号成对象，返回
     * @param service service name
     * @param method service method
     * @param params method params
     * @param url server host
     * @return object
     */
    private Object process(Class<?> service, Method method, Object[] params, String url) {
        log.info("Client proxy instance method invoke");

        // 自定义了Rpc请求的结构 RpcRequest,放入接口名称、方法名、参数
        log.info("Build Rpc request");
        RpcfxRequest rpcfxRequest = new RpcfxRequest();
        rpcfxRequest.setServiceClass(service.getName());
        rpcfxRequest.setMethod(method.getName());
        rpcfxRequest.setArgv(params);

        // 客户端使用的 netty，发送请求到服务端，拿到结果（自定义结构：rpcfxResponse)
        log.info("Client send request to Server");
        RpcfxResponse rpcfxResponse;
        try {
            rpcfxResponse = RpcNettyClientSync.getInstance().getResponse(rpcfxRequest, url);
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        log.info("Client receive response Object");
        assert rpcfxResponse != null;
        if (!rpcfxResponse.getStatus()) {
            log.info("Client receive exception");
            rpcfxResponse.getException().printStackTrace();
            return null;
        }

        // 序列化成对象返回
        log.info("Response:: " + rpcfxResponse.getResult());
        return JSON.parse(rpcfxResponse.getResult().toString());
    }
}
