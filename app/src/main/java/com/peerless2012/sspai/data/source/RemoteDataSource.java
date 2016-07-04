package com.peerless2012.sspai.data.source;

import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.NewsItem;
import com.peerless2012.sspai.domain.NewsType;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:47
 * @Version V1.0
 * @Description :
 */
public interface RemoteDataSource {

    void loadNews(NewsType newsType, int pageIndex, SimpleCallBack<List<Article>> callBack);

}
