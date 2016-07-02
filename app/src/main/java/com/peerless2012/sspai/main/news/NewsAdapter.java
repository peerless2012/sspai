package com.peerless2012.sspai.main.news;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.domain.NewsInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/28 15:53
 * @Version V1.0
 * @Description : 新闻列表适配器
 */
public class NewsAdapter extends BaseQuickAdapter<NewsInfo>{

    public NewsAdapter(List<NewsInfo> data) {
        super(R.layout.view_news_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsInfo newsInfo) {
     /*Ion.with(this).load("").asInputStream().setCallback(new FutureCallback<InputStream>() {
                @Override
                public void onCompleted(Exception e, InputStream inputStream) {
                    try {
                        GifDrawable gifDrawable = new GifDrawable(inputStream);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });*/
    }
}
