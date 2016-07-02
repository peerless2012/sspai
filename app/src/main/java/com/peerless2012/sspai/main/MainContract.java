package com.peerless2012.sspai.main;

import com.peerless2012.sspai.base.BasePresenter;
import com.peerless2012.sspai.base.BaseView;
import com.peerless2012.sspai.domain.Topic;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 21:34
 * @Version V1.0
 * @Description :
 */
public interface MainContract {

    interface MainView extends BaseView<MainPresenter>{

        void onNavDataLoaded(List<Topic> topics);

    }

    interface MainPresenter extends BasePresenter<MainView>{

        void loadNavData();

    }

}
