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



使用httpclient进行调试的时候没有遇到问题。但是在调试OKHTTP的过程中遇到一个问题，通过http://localhost:8888/hello是会返回ERR_EMPTY_RESPONSE。（还在解决中）

```java
filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求,url: /
java.lang.RuntimeException: 不支持的uri:/
	at io.github.kimmking.gateway.inbound.HttpInboundHandler$1.filter(HttpInboundHandler.java:36)
	at io.github.kimmking.gateway.inbound.HttpInboundHandler.channelRead(HttpInboundHandler.java:63)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:355)
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:102)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:355)
	at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:436)
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:321)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:295)
	at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:251)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:355)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:377)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:363)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:832)

```

