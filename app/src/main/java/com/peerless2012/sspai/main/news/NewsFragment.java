package com.peerless2012.sspai.main.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.BaseFragment;
import com.peerless2012.sspai.detail.NewsDetailActivity;
import com.peerless2012.sspai.domain.NewsInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/25 10:20
 * @Version V1.0
 * @Description : 新闻列表
 */
public class NewsFragment extends BaseFragment{

    public final static String NEWS_TYPE = "news_type";

    private RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

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
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        List<NewsInfo> newsInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            newsInfos.add(new NewsInfo());
        }
        mNewsAdapter = new NewsAdapter(newsInfos);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.openLoadAnimation();
        mNewsAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                NewsDetailActivity.launch(getContext(),null);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
