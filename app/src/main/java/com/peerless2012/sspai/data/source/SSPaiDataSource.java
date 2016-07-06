package com.peerless2012.sspai.data.source;

import android.support.annotation.NonNull;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;
import com.peerless2012.sspai.domain.NewsType;
import com.peerless2012.sspai.domain.Topic;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:49
 * @Version V1.0
 * @Description :
 */
public interface SSPaiDataSource {

    void loadNavData(@NonNull SimpleCallBack<List<Topic>> simpleCallBack);

    void loadNews(@NonNull NewsType newsType, int pageIndex, boolean force,@NonNull SimpleCallBack<List<Article>> callBack);

    void loadNewsDetail(@NonNull Article article,@NonNull SimpleCallBack<ArticleDetail> callBack);
}
