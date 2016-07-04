package com.peerless2012.sspai.main.news;

import com.peerless2012.sspai.base.BasePresenter;
import com.peerless2012.sspai.base.BaseView;
import com.peerless2012.sspai.domain.Article;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 23:31
 * @Version V1.0
 * @Description : 新闻列表
 */
public interface NewsContract {

    interface NewsView extends BaseView<NewsPresenter>{
        void onRefreshed(List<Article> articles);

        void onLoadMore(List<Article> articles);
    }

    interface NewsPresenter extends BasePresenter<NewsView>{

        void loadData(boolean force);

        void loadMore(int page);
    }

}
