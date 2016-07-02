package com.peerless2012.sspai.data.threads;

import android.os.Handler;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/23 9:14
 * @Version V1.0
 * @Description :
 */
public class ExecutorRunnable<T> implements Runnable{

    private ExecutorCallBack<T> mExecutorCallBack;

    private Handler mHandler;

    public ExecutorRunnable(ExecutorCallBack<T> mExecutorCallBack) {
        this.mExecutorCallBack = mExecutorCallBack;
    }

    public void setHandler(Handler mHandler){
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        final T t = mExecutorCallBack.doInBackground();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mExecutorCallBack.onPostExecute(t);
            }
        });
    }
}
