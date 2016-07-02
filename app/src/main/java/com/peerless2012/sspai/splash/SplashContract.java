package com.peerless2012.sspai.splash;

import com.peerless2012.sspai.base.BasePresenter;
import com.peerless2012.sspai.base.BaseView;
import com.peerless2012.sspai.domain.Topic;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:27
 * @Version V1.0
 * @Description :
 */
public interface SplashContract {

    interface SplashView extends BaseView<SplashPresenter>{

        void onDataLoaded(List<Topic> topics);

    }

    interface SplashPresenter extends BasePresenter<SplashView>{

        void initData();

    }

}
