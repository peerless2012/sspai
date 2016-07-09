package com.peerless2012.sspai.main.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.peerless2012.sspai.R;
import com.peerless2012.sspai.base.BaseFragment;
import com.peerless2012.sspai.domain.NewsType;
import com.peerless2012.sspai.domain.Topic;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/25 10:18
 * @Version V1.0
 * @Description : 话题Fragment
 */
public class TopicFragment extends BaseFragment{

    public final static String TOPIC_DATA = "topic_data";

    private View mRootView;

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private TopicPagerAdapter mTopicPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getViewLayoutRes() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void initView(View rootView) {
        mTabLayout = getView(R.id.topic_tab);
        mViewPager = getView(R.id.topic_viewpager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Topic topic = getArguments().getParcelable(TOPIC_DATA);
        List<NewsType> newsTypes = topic.getNewsTypes();
        if (newsTypes.size() <= 1) mTabLayout.setVisibility(View.GONE);
        mTopicPagerAdapter = new TopicPagerAdapter(getContext()
                ,getChildFragmentManager(),newsTypes);
        mViewPager.setAdapter(mTopicPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
