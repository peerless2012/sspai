package com.peerless2012.sspai.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.local.LocalDataSourceImpl;
import com.peerless2012.sspai.data.source.remote.RemoteDataSourceImpl;
import com.peerless2012.sspai.data.threads.ExecutorCallBack;
import com.peerless2012.sspai.data.threads.ExecutorRunnable;
import com.peerless2012.sspai.data.threads.WorkExecutor;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;
import com.peerless2012.sspai.domain.NewsType;
import com.peerless2012.sspai.domain.Topic;
import java.util.HashMap;
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

    private HashMap<String,List<Article>> mNewsInfosMap = new HashMap<String,List<Article>>();

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
    public void loadNavData(@NonNull final SimpleCallBack<List<Topic>> simpleCallBack) {
        if (mTopics != null){
            simpleCallBack.onLoaded(mTopics);
        }else {
            mLocalDataSource.loadNavData(new SimpleCallBack<List<Topic>>() {
                @Override
                public void onLoaded(List<Topic> topics) {
                    mTopics = topics;
                    simpleCallBack.onLoaded(mTopics);
                }
            });
        }
    }

    @Override
    public void loadNews(@NonNull final NewsType newsType, final int pageIndex,boolean force,@NonNull final SimpleCallBack<List<Article>> callBack) {
        List<Article> newsItems = null;
        // 如果是第一页并且非强制刷新
        if (!force){
            if (pageIndex == 1){
                newsItems = mNewsInfosMap.get(newsType.getNewsTag());
            }

            if (newsItems != null){
                callBack.onLoaded(newsItems);
            }else {
                mLocalDataSource.loadNews(newsType, pageIndex, new SimpleCallBack<List<Article>>() {
                    @Override
                    public void onLoaded(List<Article> articles) {
                        if (articles != null){
                            if (pageIndex == 1) mNewsInfosMap.put(newsType.getNewsTag(),articles);
                            callBack.onLoaded(articles);
                        }else {
                            loadNewsForce(newsType,pageIndex,callBack);
                        }
                    }
                });
            }
        }else {
            // 可能存在问题，比如由于数据更新，本地数据库的数量并不等于 每页数 * 页数
            loadNewsForce(newsType,pageIndex,callBack);
        }
    }

    @Override
    public void loadNewsDetail(@NonNull final Article article, @NonNull final SimpleCallBack<ArticleDetail> callBack) {

        mLocalDataSource.loadNewsDetail(article, new SimpleCallBack<ArticleDetail>() {
            @Override
            public void onLoaded(ArticleDetail articleDetail) {
                if (articleDetail != null){
                    callBack.onLoaded(articleDetail);
                }else {
                    mRemoteDataSource.loadNewsDetail(article,new SimpleCallBack<ArticleDetail>(){
                        @Override
                        public void onLoaded(final ArticleDetail articleDetail) {
                            if (articleDetail != null) {
                                WorkExecutor.getInstance().execute(new ExecutorRunnable<ArticleDetail>(new ExecutorCallBack<ArticleDetail>() {
                                    @Override
                                    public ArticleDetail doInBackground() {
                                        mLocalDataSource.saveNewsDetail(articleDetail);
                                        return null;
                                    }

                                    @Override
                                    public void onPostExecute(ArticleDetail articleDetail) {

                                    }
                                }));
                            }
                            callBack.onLoaded(articleDetail);
                        }
                    });
                }
            }
        });
    }

    public void loadNewsForce(@NonNull final NewsType newsType, final int pageIndex,@NonNull final SimpleCallBack<List<Article>> callBack) {
        mRemoteDataSource.loadNews(newsType, pageIndex, new SimpleCallBack<List<Article>>() {
            @Override
            public void onLoaded(final List<Article> articles) {
                if (articles != null && articles.size() >0){
                    WorkExecutor.getInstance().execute(new ExecutorRunnable<List<Article>>(new ExecutorCallBack<List<Article>>() {
                        @Override
                        public List<Article> doInBackground() {
                            mLocalDataSource.saveNews(articles);
                            return articles;
                        }

                        @Override
                        public void onPostExecute(List<Article> articles) {
                            if (pageIndex == 1) mNewsInfosMap.put(newsType.getNewsTag(),articles);
                            callBack.onLoaded(articles);
                        }
                    }));
                }
            }
        });
    }
}


