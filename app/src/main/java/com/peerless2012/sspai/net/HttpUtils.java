package com.peerless2012.sspai.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 14:34
 * @Version V1.0
 * @Description :
 */
public class HttpUtils {

    private static volatile HttpUtils sInst = null;  // <<< 这里添加了 volatile

    private OkHttpClient mOkHttpClient;

    private HttpUtils() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    public static HttpUtils getInstance() {
        HttpUtils inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HttpUtils.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new HttpUtils();
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public void t(){
        mOkHttpClient.newCall(null).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().byteStream();
            }
        });
    }
}


