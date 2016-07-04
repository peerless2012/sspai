package com.peerless2012.sspai.main.news;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.domain.Article;
import java.io.IOException;
import pl.droidsonroids.gif.GifDrawable;

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
            Ion.with(baseViewHolder.getConvertView().getContext())
                    .load(imgUrl)
                    .asByteArray()
                    .setCallback(new FutureCallback<byte[]>() {
                        @Override
                        public void onCompleted(Exception e, byte[] bytes) {
                            try {
                                GifDrawable gifDrawable = new GifDrawable(bytes);
                                baseViewHolder.setImageDrawable(R.id.news_img,gifDrawable);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }else {
            Ion.with(baseViewHolder.getConvertView().getContext())
                    .load(imgUrl)
                    .intoImageView((ImageView)baseViewHolder
                            .getView(R.id.news_img));
        }
    }
}
