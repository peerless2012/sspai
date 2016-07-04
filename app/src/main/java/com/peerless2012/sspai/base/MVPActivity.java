package com.peerless2012.sspai.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 15:45
 * @Version V1.0
 * @Description : MVP 的Activity基类
 */
public abstract class MVPActivity<V,P extends BasePresenter<V>> extends BaseActivity{

    protected P mPresenter;

    @CallSuper
    @Override
    protected void onSaveInstance(Bundle savedInstanceState) {
        super.onSaveInstance(savedInstanceState);
        mPresenter = getPresenter();
        if (mPresenter != null) mPresenter.attach(getPresenterView());
    }

    public abstract V getPresenterView();

    public abstract P getPresenter();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detach();
        super.onDestroy();
    }
}
