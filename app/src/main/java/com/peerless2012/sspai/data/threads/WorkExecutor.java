package com.peerless2012.sspai.data.threads;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/23 9:11
 * @Version V1.0
 * @Description :
 */
public class WorkExecutor {

    private Handler mHandler = null;

    private ExecutorService mExecutorService = null;

    private static volatile WorkExecutor sInst = null;  // <<< 这里添加了 volatile

    private WorkExecutor() {
        mExecutorService = Executors.newCachedThreadPool();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static WorkExecutor getInstance() {
        WorkExecutor inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (WorkExecutor.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new WorkExecutor();
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public void execute(ExecutorRunnable executorRunnable){
        executorRunnable.setHandler(mHandler);
        mExecutorService.execute(executorRunnable);
    }
}


