package com.peerless2012.sspai.data.source.local.entry;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import java.util.ArrayList;
import java.util.List;
import com.peerless2012.sspai.domain.Article;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/4 10:16
 * @Version V1.0
 * @Description :
 */
public class ArticleEntry extends BaseEntryBuilder<Article> implements BaseColumns {
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String CHAR_TYPE = " CHAR";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    public static final String TABLE_NAME = "articles";

    public static final String _TOPIC_ID = "_topicId";
    public static final String _NEWS_TYPE_ID = "_newsTypeId";
    public static final String _ARTICLE_ID = "_articleId";
    public static final String _PUBLISH_TIME = "_publishTime";
    public static final String _ARTICLE_URL = "_articleUrl";
    public static final String _IMG_URL = "_imgUrl";
    public static final String _TITLE = "_title";
    public static final String _DESC = "_desc";


    public static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ("
            + _ID +INTEGER_TYPE+ " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            +_TOPIC_ID + INTEGER_TYPE + COMMA_SEP
            + _PUBLISH_TIME  + CHAR_TYPE + COMMA_SEP
            + _NEWS_TYPE_ID  + INTEGER_TYPE + COMMA_SEP
            + _ARTICLE_ID  + CHAR_TYPE + COMMA_SEP
            + _ARTICLE_URL  + CHAR_TYPE + COMMA_SEP
            + _IMG_URL  + CHAR_TYPE + COMMA_SEP
            + _TITLE  + CHAR_TYPE + COMMA_SEP
            + _DESC  + CHAR_TYPE
            +");";

    private int idIndex = -1;
    private int topicIdIndex = -1;
    private int newsTypeIdIndex = -1;
    private int articleIdIndex = -1;
    private int publishTimeIndex = -1;
    private int articleUrlIndex = -1;
    private int imgUrlIndex = -1;
    private int titleIndex = -1;
    private int descIndex = -1;

    @Override
    public Article build(Cursor cursor) {
        generateIndex(cursor);
        return generateEntry(cursor);
    }

    @Override
    public List<Article> buildAll(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) return null;
        generateIndex(cursor);
        cursor.moveToFirst();
        List<Article> articles = new ArrayList<Article>(cursor.getCount());
        do {
            articles.add(generateEntry(cursor));
        }while (cursor.moveToNext());
        return articles;
    }


    @Override
    public ContentValues deconstruct(Article article) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_TOPIC_ID,article.getTopicId());
        contentValues.put(_NEWS_TYPE_ID,article.getNewsTypeId());
        contentValues.put(_ARTICLE_ID,article.getArticleId());
        contentValues.put(_PUBLISH_TIME,article.getPublishTime());
        contentValues.put(_ARTICLE_URL,article.getArticleUrl());
        contentValues.put(_IMG_URL,article.getImgUrl());
        contentValues.put(_TITLE,article.getTitle());
        contentValues.put(_DESC,article.getDesc());
        return contentValues;
    }

    @Override
    protected void generateIndex(Cursor cursor) {
        idIndex = cursor.getColumnIndex(_ID);
        topicIdIndex = cursor.getColumnIndex(_TOPIC_ID);
        newsTypeIdIndex = cursor.getColumnIndex(_NEWS_TYPE_ID);
        articleIdIndex = cursor.getColumnIndex(_ARTICLE_ID);
        publishTimeIndex = cursor.getColumnIndex(_PUBLISH_TIME);
        articleUrlIndex = cursor.getColumnIndex(_ARTICLE_URL);
        imgUrlIndex = cursor.getColumnIndex(_IMG_URL);
        titleIndex = cursor.getColumnIndex(_TITLE);
        descIndex = cursor.getColumnIndex(_DESC);
    }

    @Override
    protected Article generateEntry(Cursor cursor) {
        Article article = new Article();
        article.setId(cursor.getInt(idIndex));
        article.setTopicId(cursor.getInt(topicIdIndex));
        article.setNewsTypeId(cursor.getInt(newsTypeIdIndex));
        article.setArticleId(cursor.getString(articleIdIndex));
        article.setPublishTime(cursor.getString(publishTimeIndex));
        article.setArticleUrl(cursor.getString(articleUrlIndex));
        article.setImgUrl(cursor.getString(imgUrlIndex));
        article.setTitle(cursor.getString(titleIndex));
        article.setDesc(cursor.getString(descIndex));
        return article;
    }
}
