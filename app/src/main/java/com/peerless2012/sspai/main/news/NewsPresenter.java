package com.peerless2012.sspai.main.news;

import android.content.Context;

import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.SSPaiRepository;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.NewsItem;
import com.peerless2012.sspai.domain.NewsType;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 23:34
 * @Version V1.0
 * @Description : 新闻列表
 */
public class NewsPresenter implements NewsContract.NewsPresenter{

    private Context mContext;

    private SSPaiRepository mSsPaiRepository;

    private NewsContract.NewsView mNewsView;

    private NewsType mNewsType;

    public NewsPresenter(Context context,NewsType newsType) {
        this.mContext = context;
        this.mNewsType = newsType;
        mSsPaiRepository = SSPaiRepository.getInstance(mContext);
    }
    @Override
    public void attach(NewsContract.NewsView newsView) {
        mNewsView = newsView;
    }

    @Override
    public void detach() {
        mSsPaiRepository = null;
        mNewsView = null;
        mContext = null;
    }

    @Override
    public void loadData( boolean force) {
        mSsPaiRepository.loadNews(mNewsType, 1, force,new SimpleCallBack<List<Article>>() {
            @Override
            public void onLoaded(List<Article> articles) {
                if (mNewsView != null) mNewsView.onRefreshed(articles);
            }
        });
    }

    @Override
    public void loadMore(int page) {
        mSsPaiRepository.loadNews(mNewsType, page, false, new SimpleCallBack<List<Article>>() {
            @Override
            public void onLoaded(List<Article> articles) {
                if (mNewsView != null) mNewsView.onLoadMore(articles);
            }
        });
    }
}
