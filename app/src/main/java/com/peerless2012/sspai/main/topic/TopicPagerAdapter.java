package com.peerless2012.sspai.main.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.peerless2012.sspai.domain.NewsType;
import com.peerless2012.sspai.main.news.NewsFragment;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 14:50
 * @Version V1.0
 * @Description : 话题ViewPager适配器
 */
public class TopicPagerAdapter extends FragmentPagerAdapter{

    private Context mContext;

    private List<NewsType> mNewsTypes;

    public TopicPagerAdapter(Context context,FragmentManager fm,List<NewsType> newsTypes) {
        super(fm);
        mContext = context;
        mNewsTypes = newsTypes;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NewsFragment.NEWS_TYPE,mNewsTypes.get(position));
        return Fragment.instantiate(mContext,NewsFragment.class.getName(),bundle);
    }

    @Override
    public int getCount() {
        return mNewsTypes == null ? 0 : mNewsTypes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNewsTypes.get(position).getTypeName();
    }
}
