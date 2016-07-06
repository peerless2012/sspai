package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/6 12:09
 * @Version V1.0
 * @Description : 文章详情
 */
public class ArticleDetail implements Parcelable{

    private int id;

    private String articleId;

    private String articleContent;

    public ArticleDetail() {
    }

    protected ArticleDetail(Parcel in) {
        id = in.readInt();
        articleId = in.readString();
        articleContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(articleId);
        dest.writeString(articleContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleDetail> CREATOR = new Creator<ArticleDetail>() {
        @Override
        public ArticleDetail createFromParcel(Parcel in) {
            return new ArticleDetail(in);
        }

        @Override
        public ArticleDetail[] newArray(int size) {
            return new ArticleDetail[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Override
    public String toString() {
        return "ArticleDetail{" +
                "id=" + id +
                ", articleId='" + articleId + '\'' +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }
}
