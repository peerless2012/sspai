package com.peerless2012.sspai.detail;

import com.peerless2012.sspai.base.BasePresenter;
import com.peerless2012.sspai.base.BaseView;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 22:51
 * @Version V1.0
 * @Description :
 */
public interface NewsDetailContract {

    interface NewsDetailView extends BaseView<NewsDetailPresenter>{

        void onStartLoad();

        void onWebLoaded(ArticleDetail articleDetail);

        void onLoadFailed(String errorMsg);
    }

    interface NewsDetailPresenter extends BasePresenter<NewsDetailView>{

        void loadWebContent(Article article);

    }
}
