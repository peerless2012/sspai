package com.peerless2012.sspai.main.news;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.MVPFragment;
import com.peerless2012.sspai.detail.NewsDetailActivity;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.NewsItem;
import com.peerless2012.sspai.domain.NewsType;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/25 10:20
 * @Version V1.0
 * @Description : 新闻列表
 */
public class NewsFragment extends MVPFragment<NewsContract.NewsView,NewsContract.NewsPresenter>
                            implements NewsContract.NewsView,SwipeRefreshLayout.OnRefreshListener{

    public final static String NEWS_TYPE = "news_type";

    private RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

    private int pageIndex = 1;

    private NewsType mNewsType;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onSaveInstance(Bundle savedInstanceState) {
        mNewsType = getArguments().getParcelable(NEWS_TYPE);
        super.onSaveInstance(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getViewLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = getView(R.id.news_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mSwipeRefreshLayout = getView(R.id.swipe_refresh);
        mNewsAdapter = new NewsAdapter();
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mNewsAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                NewsDetailActivity.launch(getContext(),mNewsAdapter.getItem(i));
            }
        });
        mNewsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(pageIndex + 1);
            }
        });
    }

    @Override
    protected void initData() {
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.openLoadAnimation();
        mSwipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    mSwipeRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else {
                    mSwipeRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.loadData(false);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public NewsContract.NewsView getPresenterView() {
        return this;
    }

    @Override
    public NewsContract.NewsPresenter getPresenter() {
        return new NewsPresenter(getContext(),mNewsType);
    }

    @Override
    public void setPresenter(NewsContract.NewsPresenter presenter) {

    }

    @Override
    public void onRefreshed(List<Article> articles) {
        if (articles != null && articles.size() > 0){
            mNewsAdapter.setNewData(articles);
            pageIndex = 0;
        }else {
            // 提示刷新失败
            toast(R.string.refresh_fail);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore(List<Article> newsItems) {
        if (newsItems != null && newsItems.size() > 0){
            pageIndex ++;
            mNewsAdapter.addData(newsItems);
        }else {
            // 提示加载更多失败
            toast(R.string.load_fail);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.loadData(true);
    }
}
