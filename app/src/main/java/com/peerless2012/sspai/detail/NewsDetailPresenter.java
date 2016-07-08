package com.peerless2012.sspai.detail;

import android.content.Context;

import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.SSPaiRepository;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 23:24
 * @Version V1.0
 * @Description :
 */
public class NewsDetailPresenter implements NewsDetailContract.NewsDetailPresenter{

    private Context mContext;

    private SSPaiRepository mSsPaiRepository;

    private NewsDetailContract.NewsDetailView mNewsDetailView;

    public NewsDetailPresenter(Context mContext) {
        this.mContext = mContext;
        mSsPaiRepository = SSPaiRepository.getInstance(mContext);
    }

    @Override
    public void attach(NewsDetailContract.NewsDetailView newsDetailView) {
        mNewsDetailView = newsDetailView;
    }

    @Override
    public void detach() {
        mSsPaiRepository = null;
        mNewsDetailView = null;
        mContext = null;
    }

    @Override
    public void loadWebContent(Article article) {
        mSsPaiRepository.loadNewsDetail(article, new SimpleCallBack<ArticleDetail>() {
            @Override
            public void onLoaded(ArticleDetail articleDetail) {
                if (mNewsDetailView != null){
                    if (articleDetail != null){
                        mNewsDetailView.onWebLoaded(articleDetail);
                    }else {
                        mNewsDetailView.onLoadFailed("获取失败！");
                    }
                }
            }
        });
    }
}
