package com.peerless2012.sspai.data.source.local.entry;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.peerless2012.sspai.domain.ArticleDetail;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/6 12:08
 * @Version V1.0
 * @Description :
 */
public class ArticleDetailEntry extends BaseEntryBuilder<ArticleDetail> implements BaseColumns{

    public static final String TABLE_NAME = "article_detail";

    public static final String _ARTICLE_ID = "_articleId";
    public static final String _ARTICLE_CONTENT = "_articleContent";

    public static final String CREATE_SQL = SQL_HEADER + TABLE_NAME + SQL_PRE
            + _ID +INTEGER_TYPE + PRIMARY_KEY + AUTOINCREMENT
            + COMMA_SEP + _ARTICLE_ID  + CHAR_TYPE
            + COMMA_SEP + _ARTICLE_CONTENT  + CHAR_TYPE
            + SQL_AFTER;

    private int idIndex = -1;
    private int articleIdIndex = -1;
    private int articleContentIndex = -1;

    @Override
    public ContentValues deconstruct(ArticleDetail articleDetail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ARTICLE_ID,articleDetail.getArticleId());
        contentValues.put(_ARTICLE_CONTENT,articleDetail.getArticleContent());
        return contentValues;
    }

    @Override
    protected void generateIndex(Cursor cursor) {
        idIndex = cursor.getColumnIndex(_ID);
        articleIdIndex = cursor.getColumnIndex(_ARTICLE_ID);
        articleContentIndex = cursor.getColumnIndex(_ARTICLE_CONTENT);
    }

    @Override
    protected ArticleDetail generateEntry(Cursor cursor) {
        ArticleDetail articleDetail = new ArticleDetail();
        if (idIndex >= 0 ) articleDetail.setId(cursor.getInt(idIndex));
        if (articleIdIndex >= 0 ) articleDetail.setArticleId(cursor.getString(articleIdIndex));
        if (articleContentIndex >= 0 ) articleDetail.setArticleContent(cursor.getString(articleContentIndex));
        return articleDetail;
    }

    @Override
    protected void resetIndexs() {
        idIndex = -1;
        articleIdIndex = -1;
        articleContentIndex = -1;
    }
}
