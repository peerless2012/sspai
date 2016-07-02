package com.peerless2012.sspai.splash;

import android.animation.Animator;
import com.peerless2012.sspai.base.MVPActivity;
import com.peerless2012.sspai.domain.Topic;
import com.peerless2012.sspai.main.MainActivity;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.view.widget.SSPaiView;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/25 10:25
 * @Version V1.0
 * @Description :
 */
public class SplashActivity extends MVPActivity<SplashContract.SplashView,SplashContract.SplashPresenter>
        implements Animator.AnimatorListener,SplashContract.SplashView {

    private SSPaiView mSSPaiView;

    private boolean isDaTaInited = false;

    private boolean isAnimated = false;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mSSPaiView = getView(R.id.splash_logo);
    }

    @Override
    protected void initListener() {
        mSSPaiView.addOnAnimateListener(this);
    }

    @Override
    protected void initData() {
        mPresenter.initData();
    }

    @Override
    public SplashContract.SplashView getPresenterView() {
        return this;
    }

    @Override
    public SplashContract.SplashPresenter getPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isAnimated = true;
        jumpToMain();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onDataLoaded(List<Topic> topics) {
        isDaTaInited = true;
        jumpToMain();
    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {

    }

    private void jumpToMain(){
        if (isDaTaInited && isAnimated){
            MainActivity.launch(this);
            finish();
        }
    }
}
