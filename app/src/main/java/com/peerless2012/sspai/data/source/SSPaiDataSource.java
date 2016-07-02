package com.peerless2012.sspai.data.source;

import com.peerless2012.sspai.data.callback.SimpleCallBack;
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

    void loadNavData(SimpleCallBack<List<Topic>> simpleCallBack);
}
