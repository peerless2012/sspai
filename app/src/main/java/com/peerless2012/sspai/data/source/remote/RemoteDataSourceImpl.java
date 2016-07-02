package com.peerless2012.sspai.data.source.remote;

import android.content.Context;

import com.peerless2012.sspai.data.source.RemoteDataSource;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:49
 * @Version V1.0
 * @Description :
 */
public class RemoteDataSourceImpl implements RemoteDataSource {

    private Context mContext;

    private static volatile RemoteDataSourceImpl sInst = null;  // <<< 这里添加了 volatile

    private RemoteDataSourceImpl(Context context) {
        mContext = context.getApplicationContext();
    }

    public static RemoteDataSourceImpl getInstance(Context context) {
        RemoteDataSourceImpl inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (RemoteDataSourceImpl.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new RemoteDataSourceImpl(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }
}


