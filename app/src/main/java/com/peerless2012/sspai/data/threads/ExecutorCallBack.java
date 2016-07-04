package com.peerless2012.sspai.data.threads;

import com.peerless2012.sspai.R;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/23 9:15
 * @Version V1.0
 * @Description :
 */
public interface ExecutorCallBack<T> {

    T doInBackground();

    void onPostExecute(T t);
}
