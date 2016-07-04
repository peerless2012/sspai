package com.peerless2012.sspai.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 15:42
 * @Version V1.0
 * @Description :
 */
public abstract class MVPFragment<V,P extends BasePresenter<V>> extends BaseFragment{

    protected P mPresenter;

    @Override
    protected void onSaveInstance(Bundle savedInstanceState) {
        super.onSaveInstance(savedInstanceState);
        mPresenter = getPresenter();
        if (mPresenter != null) mPresenter.attach(getPresenterView());
    }

    public abstract V getPresenterView();

    public abstract P getPresenter();

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.detach();
        super.onDestroy();
    }
}
