package com.peerless2012.sspai.detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.MVPActivity;
import com.peerless2012.sspai.domain.NewsInfo;

public class NewsDetailActivity extends MVPActivity<NewsDetailContract.NewsDetailView,NewsDetailContract.NewsDetailPresenter>
                                implements NewsDetailContract.NewsDetailView{

    private FloatingActionButton mFab;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        mFab = getView(R.id.fab);
    }

    @Override
    protected void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void launch(Context context, NewsInfo newsInfo){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public NewsDetailContract.NewsDetailView getPresenterView() {
        return this;
    }

    @Override
    public NewsDetailContract.NewsDetailPresenter getPresenter() {
        return null;
    }

    @Override
    public void setPresenter(NewsDetailContract.NewsDetailPresenter presenter) {

    }
}
