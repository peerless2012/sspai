package com.peerless2012.sspai.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.peerless2012.sspai.R;
import com.peerless2012.sspai.data.callback.SimpleCallBack;
import com.peerless2012.sspai.data.source.LocalDataSource;
import com.peerless2012.sspai.data.source.local.entry.ArticleDetailEntry;
import com.peerless2012.sspai.data.source.local.entry.ArticleEntry;
import com.peerless2012.sspai.data.threads.ExecutorCallBack;
import com.peerless2012.sspai.data.threads.ExecutorRunnable;
import com.peerless2012.sspai.data.threads.WorkExecutor;
import com.peerless2012.sspai.domain.Article;
import com.peerless2012.sspai.domain.ArticleDetail;
import com.peerless2012.sspai.domain.NewsType;
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
public class LocalDataSourceImpl extends BaseLocalDataSource implements LocalDataSource{

    private static volatile LocalDataSourceImpl sInst = null;  // <<< 这里添加了 volatile

    private Context mContext;
    private ArticleEntry mArticleEntry;
    private ArticleDetailEntry mArticleDetailEntry;
    public LocalDataSourceImpl(Context context) {
        super(SSPaiDBHelper.getInstance(context.getApplicationContext()));
        mContext = context.getApplicationContext();
        mArticleEntry = new ArticleEntry();
        mArticleDetailEntry = new ArticleDetailEntry();
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

    @Override
    public void loadNews(final NewsType newsType, int pageIndex, final SimpleCallBack<List<Article>> callBack) {
        WorkExecutor.getInstance().execute(new ExecutorRunnable<List<Article>>(new ExecutorCallBack<List<Article>>() {
            @Override
            public List<Article> doInBackground() {
                synchronized (LOCK){
                    List<Article> articleList = null;
                    SQLiteDatabase database = null;
                        try {
                            database = getWritableDatabase();
                            Cursor cursor = database.query(ArticleEntry.TABLE_NAME,
                                null,ArticleEntry._TOPIC_ID +" = ? AND "+ArticleEntry._NEWS_TYPE_ID+" = ?"
                                ,new String[]{String.valueOf(newsType.getTopicId()),String.valueOf(newsType.getTypeId())}
                                ,null,null,ArticleEntry._ARTICLE_ID + " DESC","20");

                        if (cursor != null && cursor.getCount() > 0){
                            articleList = mArticleEntry.buildAll(cursor);
                        }
                        return articleList;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        closeDatabase(database);
                    }
                    return null;
                }
            }

            @Override
            public void onPostExecute(List<Article> articles) {
                if (callBack != null) callBack.onLoaded(articles);
            }
        }));
    }

    @Override
    public void saveNews(List<Article> articles) {
        if (articles == null || articles.size() == 0) return;
        synchronized (LOCK){
            SQLiteDatabase database = null;
            try {
                database = getWritableDatabase();
                database.beginTransaction();
                Article article;
                for (int i = 0; i < articles.size(); i++) {
                    article = articles.get(i);
                    database.delete(ArticleEntry.TABLE_NAME,ArticleEntry._ARTICLE_ID + " = ?",new String[]{article.getArticleId()});
                    database.insert(ArticleEntry.TABLE_NAME,null,mArticleEntry.deconstruct(article));
                }
                database.setTransactionSuccessful();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (database != null) database.endTransaction();
                closeDatabase(database);
            }
        }
    }

    @Override
    public void loadNewsDetail(Article article, SimpleCallBack<ArticleDetail> callBack) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = getReadableDatabase();
            cursor = database.query(ArticleDetailEntry.TABLE_NAME
                    ,null,ArticleDetailEntry._ARTICLE_ID + " = ?"
                    ,new String[]{article.getArticleId()},null,null,null);
            if (cursor != null && cursor.getCount() > 0){
                ArticleDetail articleDetail = mArticleDetailEntry.build(cursor);
                callBack.onLoaded(articleDetail);
            }else {
                callBack.onLoaded(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onLoaded(null);
        } finally {
            closeCursor(cursor);
            closeDatabase(database);
        }
    }

    @Override
    public void saveNewsDetail(ArticleDetail articleDetail) {
        synchronized (LOCK){
            SQLiteDatabase database = null;
            try {
                database = getReadableDatabase();
                database.beginTransaction();
                database.insert(ArticleDetailEntry.TABLE_NAME,
                        null,mArticleDetailEntry.deconstruct(articleDetail));
                database.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (database != null) database.endTransaction();
                closeDatabase(database);
            }
        }
    }
}


