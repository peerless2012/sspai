package com.peerless2012.sspai.detail;

import com.peerless2012.sspai.base.BasePresenter;
import com.peerless2012.sspai.base.BaseView;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 22:51
 * @Version V1.0
 * @Description :
 */
public interface NewsDetailContract {

    interface NewsDetailView extends BaseView<NewsDetailPresenter>{

    }

    interface NewsDetailPresenter extends BasePresenter<NewsDetailView>{

    }
}
