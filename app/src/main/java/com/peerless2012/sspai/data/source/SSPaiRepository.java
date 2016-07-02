package com.peerless2012.sspai.data.source;

import android.content.Context;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.local.LocalDataSourceImpl;
import com.peerless2012.sspai.data.source.remote.RemoteDataSourceImpl;
import com.peerless2012.sspai.domain.Topic;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:49
 * @Version V1.0
 * @Description :
 */
public class SSPaiRepository implements SSPaiDataSource {

    private Context mContext;

    private LocalDataSource mLocalDataSource;

    private RemoteDataSource mRemoteDataSource;

    private static volatile SSPaiRepository sInst = null;  // <<< 这里添加了 volatile

    private SSPaiRepository(Context context) {
        mContext = context.getApplicationContext();
        mLocalDataSource = LocalDataSourceImpl.getInstance(mContext);
        mRemoteDataSource = RemoteDataSourceImpl.getInstance(mContext);
    }

    public static SSPaiRepository getInstance(Context context) {
        SSPaiRepository inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (SSPaiRepository.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new SSPaiRepository(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }


    /*--------------------------------普通方法区-------------------------------*/

    private List<Topic> mTopics;

    @Override
    public void loadNavData(final SimpleCallBack<List<Topic>> simpleCallBack) {
        if (mTopics != null){
            if (simpleCallBack != null) simpleCallBack.onLoaded(mTopics);
        }else {
            mLocalDataSource.loadNavData(new SimpleCallBack<List<Topic>>() {
                @Override
                public void onLoaded(List<Topic> topics) {
                    mTopics = topics;
                    if (simpleCallBack != null) simpleCallBack.onLoaded(mTopics);
                }
            });
        }
    }
}


