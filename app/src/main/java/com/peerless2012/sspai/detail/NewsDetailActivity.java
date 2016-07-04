package com.peerless2012.sspai.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.MVPActivity;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.NewsItem;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class NewsDetailActivity extends MVPActivity<NewsDetailContract.NewsDetailView,NewsDetailContract.NewsDetailPresenter>
                                implements NewsDetailContract.NewsDetailView{

    public final static String ARTILE_TAG = "artile_tag";

    private Article mArticle;

    private FloatingActionButton mFab;

    private ImageView mNewsImg;

    private WebView mNewsContent;

    @Override
    protected void onSaveInstance(Bundle savedInstanceState) {
        mArticle = getIntent().getParcelableExtra(ARTILE_TAG);
        super.onSaveInstance(savedInstanceState);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        mFab = getView(R.id.fab);
        mNewsImg = getView(R.id.news_img);
        mNewsContent = getView(R.id.news_content);

        String imgUrl = mArticle.getImgUrl();
        if (imgUrl.endsWith(".gif")){
            Ion.with(this)
                    .load(imgUrl)
                    .asByteArray()
                    .setCallback(new FutureCallback<byte[]>() {
                        @Override
                        public void onCompleted(Exception e, byte[] bytes) {
                            try {
                                GifDrawable gifDrawable = new GifDrawable(bytes);
                                mNewsImg.setImageDrawable(gifDrawable);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }else {
            Ion.with(mNewsImg)
                    .load(imgUrl);
        }
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
        mNewsContent.loadUrl(mArticle.getArticleUrl());
    }

    public static void launch(Context context, Article article){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(ARTILE_TAG,article);
        context.startActivity(intent);
    }

    @Override
    public NewsDetailContract.NewsDetailView getPresenterView() {
        return this;
    }

    @Override
    public NewsDetailContract.NewsDetailPresenter getPresenter() {
        return new NewsDetailPresenter(this);
    }

    @Override
    public void setPresenter(NewsDetailContract.NewsDetailPresenter presenter) {

    }
}
