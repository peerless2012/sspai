package com.peerless2012.sspai.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.MVPActivity;
import com.peerless2012.sspai.domain.Topic;
import com.peerless2012.sspai.main.topic.TopicFragment;

import java.util.List;


public class MainActivity extends MVPActivity<MainContract.MainView,MainContract.MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener,MainContract.MainView{

    private FloatingActionButton mFab;

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView mNavigationView;

    private List<Topic> mTopics;

    private int selectedPosition = -1;

    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.SimpleDrawerListener(){
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            if (selectedPosition >= 0){
                changeFragmentByPosition(selectedPosition);
            }
            selectedPosition = -1;
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFab = getView(R.id.fab);
        mDrawerLayout = getView(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView = getView(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mDrawerLayout.addDrawerListener(mDrawerListener);
    }

    @Override
    protected void initData() {
        mPresenter.loadNavData();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_android) {
            selectedPosition = 0;
        } else if (id == R.id.nav_ios) {
            selectedPosition = 1;
        } else if (id == R.id.nav_mac) {
            selectedPosition = 2;
        } else if (id == R.id.nav_device) {
            selectedPosition = 3;
        } else if (id == R.id.nav_delete) {
            //
        } else if (id == R.id.nav_about) {
            //
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerToggle);
        mDrawerLayout.removeDrawerListener(mDrawerListener);
        super.onDestroy();
    }

    private Fragment preFragment;

    /**
     * @param fragmentName Fragment的完整类名
     * @param tag Tag名称（添加的时候带上，可以根据tag找到添加的fragment）
     * @param data 切换fragment需要携带的数据
     */
    private void changeFragment(String fragmentName,String tag,Bundle data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //根据tag找到对应的Fragment
        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);
        //如果选中的就是现在显示的，直接返回（如果需求不同则去掉本行）
        if (currentFragment != null && currentFragment == preFragment) return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment == null) {

            //参数对应到函数的注释上面,这样写的好处是，如果fragment被回收，在fragment被重新创建的时候会携带之前传递的数据
            currentFragment = Fragment.instantiate(this, fragmentName, data);
            transaction.add(R.id.content, currentFragment, tag);
            if (preFragment != null) {
                transaction.hide(preFragment);
            }
        }else {
            transaction.hide(preFragment);
            transaction.show(currentFragment);
        }
        preFragment = currentFragment;
        transaction.commit();
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    public MainContract.MainView getPresenterView() {
        return this;
    }

    @Override
    public MainContract.MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onNavDataLoaded(List<Topic> topics) {
        mTopics = topics;
        changeFragmentByPosition(0);
    }

    @Override
    public void setPresenter(MainContract.MainPresenter presenter) {

    }

    private void changeFragmentByPosition(int position){
        Topic topic = mTopics.get(position);
        setTitle(topic.getTopicName());
        Bundle bundle = new Bundle();
        bundle.putParcelable(TopicFragment.TOPIC_DATA,topic);
        changeFragment(TopicFragment.class.getName(),topic.getTopicTag(),bundle);
    }

    @Override
    protected boolean isHome() {
        return true;
    }
}
