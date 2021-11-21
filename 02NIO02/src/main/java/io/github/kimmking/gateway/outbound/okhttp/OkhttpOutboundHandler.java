package io.github.kimmking.gateway.outbound.okhttp;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import com.server.OKHttpUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkhttpOutboundHandler {

    private String backendUrl;

    public OkhttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String url = this.backendUrl + fullRequest.uri();
        System.out.println("===============" + url);
        FullHttpResponse response = null;
        try {
            //
            String body = OKHttpUtil.getByOKHttp(url);
            byte[] bytesArray = body.getBytes("UTF-8");
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytesArray));
            response.headers().set("Content-Type", "application/json");
            response.headers().set("X-proxy-tag", this.getClass().getSimpleName());
            response.headers().setInt("Content-Length", bytesArray.length);
        } catch (Throwable e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if(null == response){
                    response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
                }
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
