package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HeaderHttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.github.kimmking.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private OkhttpOutboundHandler handler;
    private HttpRequestFilter filter = new HeaderHttpRequestFilter();
    
    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        this.handler = new OkhttpOutboundHandler(this.proxyServer);
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
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
//            String uri = fullRequest.uri();
//            //logger.info("接收到的请求url为{}", uri);
//            if (uri.contains("/test")) {
//                handlerTest(fullRequest, ctx);
//            }
            //请求拦截
            filter.filter(fullRequest, ctx);
            handler.handle(fullRequest, ctx);
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

//    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
//        FullHttpResponse response = null;
//        try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());
//
//        } catch (Exception e) {
//            logger.error("处理测试接口出错", e);
//            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
//        } finally {
//            if (fullRequest != null) {
//                if (!HttpUtil.isKeepAlive(fullRequest)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    response.headers().set(CONNECTION, KEEP_ALIVE);
//                    ctx.write(response);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }

}
