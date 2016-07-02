package com.peerless2012.sspai.main;

import android.content.Context;

import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.SSPaiRepository;
import com.peerless2012.sspai.domain.Topic;
import com.peerless2012.sspai.main.MainContract;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 21:37
 * @Version V1.0
 * @Description :
 */
public class MainPresenter implements MainContract.MainPresenter{

    private Context mContext;

    private SSPaiRepository mSsPaiRepository;

    private MainContract.MainView mMainView;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
        mSsPaiRepository = SSPaiRepository.getInstance(mContext);
    }

    @Override
    public void loadNavData() {
        mSsPaiRepository.loadNavData(new SimpleCallBack<List<Topic>>() {
            @Override
            public void onLoaded(List<Topic> topics) {
                if (mMainView != null) mMainView.onNavDataLoaded(topics);
            }
        });
    }

    @Override
    public void attach(MainContract.MainView mainView) {
        mMainView = mainView;
    }

    @Override
    public void detach() {
        mContext = null;
        mMainView = null;
        mSsPaiRepository = null;
    }
}
