package com.peerless2012.sspai.data.source.local;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.LocalDataSource;
import com.peerless2012.sspai.data.threads.ExecutorCallBack;
import com.peerless2012.sspai.data.threads.ExecutorRunnable;
import com.peerless2012.sspai.data.threads.WorkExecutor;
import com.peerless2012.sspai.domain.Topic;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/2 16:48
 * @Version V1.0
 * @Description :
 */
public class LocalDataSourceImpl implements LocalDataSource{

    private static volatile LocalDataSourceImpl sInst = null;  // <<< 这里添加了 volatile

    private Context mContext;

    private LocalDataSourceImpl(Context context) {
        mContext = context.getApplicationContext();
    }

    public static LocalDataSourceImpl getInstance(Context context) {
        LocalDataSourceImpl inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (LocalDataSourceImpl.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new LocalDataSourceImpl(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    /*--------------------------------方法区-----------------------------------*/

    @Override
    public void loadNavData(final SimpleCallBack<List<Topic>> simpleCallBack) {
        WorkExecutor.getInstance().execute(new ExecutorRunnable<List<Topic>>(new ExecutorCallBack<List<Topic>>() {
            @Override
            public List<Topic> doInBackground() {
                List<Topic> topics = null;
                try {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                Type typeToken = new TypeToken<List<Topic>>(){}.getType();
                InputStream inputStream = mContext.getResources().openRawResource(R.raw.dirs);
                Reader reader = new InputStreamReader(inputStream,"UTF-8");
                topics = gson.fromJson(reader, typeToken);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return topics;
            }

            @Override
            public void onPostExecute(List<Topic> topics) {
                if (simpleCallBack != null) simpleCallBack.onLoaded(topics);
            }
        }));
    }
}


