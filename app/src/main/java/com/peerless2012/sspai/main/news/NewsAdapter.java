package com.peerless2012.sspai.main.news;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peerless2012.sspai.domain.Article;
import java.io.File;
import java.io.IOException;
import pl.droidsonroids.gif.GifDrawable;
import com.peerless2012.sspai.R;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/28 15:53
 * @Version V1.0
 * @Description : 新闻列表适配器
 */
public class NewsAdapter extends BaseQuickAdapter<Article>{

    public NewsAdapter() {
        super(R.layout.view_news_item, null);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, Article article) {
        baseViewHolder.setText(R.id.news_title,article.getTitle())
                .setText(R.id.news_desc,article.getDesc());
        String imgUrl = article.getImgUrl();
        if (imgUrl.endsWith(".gif")){
            Glide.with(baseViewHolder.getConvertView().getContext())
                    .load(imgUrl)
                    .downloadOnly(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                            try {
                                GifDrawable gifDrawable = new GifDrawable(resource);
                                baseViewHolder.setImageDrawable(R.id.news_img,gifDrawable);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }else {
            Glide.with(baseViewHolder.getConvertView().getContext())
                    .load(imgUrl)
                    .into((ImageView)baseViewHolder
                            .getView(R.id.news_img));
        }
    }
}
