package com.server;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OKHttpUtil {

    public static OkHttpClient client = new OkHttpClient().newBuilder()
            .callTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .build();

    public static String getByOKHttp(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("url" + url + " ; response: \n" + response);
            return response.body().string();
        }finally {
            client.clone();
        }
    }
    public static void main(String[] args) {

    }
}
