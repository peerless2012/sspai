package com.peerless2012.sspai.splash;

import android.content.Context;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.SSPaiRepository;
import com.peerless2012.sspai.domain.Topic;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:41
 * @Version V1.0
 * @Description :
 */
public class SplashPresenter implements SplashContract.SplashPresenter{

    private Context mContext;

    private SSPaiRepository mSsPaiRepository;

    private SplashContract.SplashView mSplashView;

    public SplashPresenter(Context mContext) {
        this.mContext = mContext;
        mSsPaiRepository = SSPaiRepository.getInstance(mContext);
    }

    @Override
    public void initData() {
        mSsPaiRepository.loadNavData(new SimpleCallBack<List<Topic>>() {
            @Override
            public void onLoaded(List<Topic> topics) {
                if (mSplashView != null) mSplashView.onDataLoaded(topics);
            }
        });
    }

    @Override
    public void attach(SplashContract.SplashView splashView) {
        mSplashView = splashView;
    }

    @Override
    public void detach() {
        mContext = null;
        mSsPaiRepository = null;
        mSplashView = null;
    }
}
