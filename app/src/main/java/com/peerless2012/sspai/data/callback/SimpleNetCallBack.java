package com.peerless2012.sspai.data.callback;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:56
 * @Version V1.0
 * @Description :
 */
public interface SimpleNetCallBack<D> {
    void onSuccess(D d);
    void onFaild(int errorCode,String errorMsg);
}
