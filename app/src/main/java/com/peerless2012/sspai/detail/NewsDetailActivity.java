package com.peerless2012.sspai.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.peerless2012.sspai.App;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.MVPActivity;
import com.peerless2012.sspai.common.utils.ShareUtils;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;

import java.io.File;
import java.io.IOException;
import pl.droidsonroids.gif.GifDrawable;

public class NewsDetailActivity extends MVPActivity<NewsDetailContract.NewsDetailView,NewsDetailContract.NewsDetailPresenter>
                                implements NewsDetailContract.NewsDetailView{
    // http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0417/2736.html
    public final static String ARTICLE_TAG = "article_tag";

    private Article mArticle;

    private FloatingActionButton mFab;

    private ImageView mNewsImg;

    private WebView mNewsContent;

    private ProgressBar mProgressBar;

    private NestedScrollView mNestedScrollView;

    @Override
    protected void onSaveInstance(Bundle savedInstanceState) {
        mArticle = getIntent().getParcelableExtra(ARTICLE_TAG);
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
        mNestedScrollView = getView(R.id.content_scrollview);
        mNestedScrollView.setFillViewport(true);
        String imgUrl = mArticle.getImgUrl();
        if (imgUrl.endsWith(".gif")){
            Glide.with(this)
                    .load(imgUrl)
                    .downloadOnly(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                            try {
                                GifDrawable gifDrawable = new GifDrawable(resource);
                                mNewsImg.setImageDrawable(gifDrawable);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }else {
            Glide.with(this)
                    .load(imgUrl)
                    .into(mNewsImg);
        }

        WebSettings settings = mNewsContent.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);

        // 试了很多方法，并没有什么卵用
        //设置数据库缓存路径
        //设置  Application Caches 缓存目录
        File cacheDirPath = ((App)getApplication()).getAppCacheDir();
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(cacheDirPath.getAbsolutePath());
        settings.setDatabasePath(cacheDirPath.getAbsolutePath());
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
    }

    private boolean isRealod = true;

    private int sceenWidth = 0;

    @Override
    protected void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareWeb(NewsDetailActivity.this
                        ,mArticle.getTitle()
                        ,mArticle.getDesc()
                        ,mArticle.getArticleUrl()
                        , SendMessageToWX.Req.WXSceneFavorite);
            }
        });
        IWXAPI api = ShareUtils.getApi();
    }

    @Override
    protected void initData() {
        setTitle(mArticle.getTitle());
        mPresenter.loadWebContent(mArticle);
    }

    @Override
    protected void onDestroy() {
        // https://www.zhihu.com/question/31316646
        mNewsContent.destroy();
        super.onDestroy();
    }

    public static void launch(Context context, Article article){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(ARTICLE_TAG,article);
        context.startActivity(intent);
    }

    @Override
    public @NonNull NewsDetailContract.NewsDetailView getPresenterView() {
        return this;
    }

    @Override
    public @NonNull NewsDetailContract.NewsDetailPresenter getPresenter() {
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
                mNestedScrollView.setFillViewport(false);
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
