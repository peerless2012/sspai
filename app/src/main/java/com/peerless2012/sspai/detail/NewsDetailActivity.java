package com.peerless2012.sspai.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.MVPActivity;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;
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

    private ProgressBar mProgressBar;

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
        mProgressBar = getView(R.id.loading_progress);

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

        WebSettings settings = mNewsContent.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//            settings.setLoadWithOverviewMode(true);
//            settings.setSupportZoom(false);
//        }else {
//            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        }
    }

    private boolean isRealod = true;

    private int sceenWidth = 0;

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
        setTitle(mArticle.getTitle());
        mPresenter.loadWebContent(mArticle);
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

    @Override
    public void onStartLoad() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onWebLoaded(final ArticleDetail articleDetail) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
                mNewsContent.loadData(articleDetail.getArticleContent(),"text/html; charset=UTF-8", null);
            }
        });
    }

    @Override
    public void onLoadFailed(String errorMsg) {
        mProgressBar.setVisibility(View.GONE);
        toast(errorMsg);
    }
}
