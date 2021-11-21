# netty-gateway

```
   

```



1.（必做）整合你上次作业的 httpclient/okhttp；

我上次写的OKHttp直接在main方法中实现了client创建，通过client完成了http请求。所以本次作业我重新编写了相应的方法：client创建，http请求。然后我实现了OkhttpOutboundHandler.java类。运行BackendServer.java访问8088得到结果如下

```java
hello nio
```



2.（必做）实现过滤器。

HttpInboundHandler.java初始化的时候加载了拦截器的方法。

``````java
this.filter = new HttpRequestFilter() {
            @Override
            public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
                String uri = fullRequest.uri();
                System.out.println(" filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求,url: " + uri);
                if (uri.startsWith("/hello")) {
                    // 放过
                } else {
                    throw new RuntimeException("不支持的uri:" + uri);
                }
                HttpHeaders headers = fullRequest.headers();
                if (headers == null) {
                    headers = new DefaultHttpHeaders();
                }
                headers.add("proxy-tag", this.getClass().getSimpleName());
            }
        };
``````



![image-20211121231747355](/Users/zhaobaozhi/Library/Application Support/typora-user-images/image-20211121231747355.png)