package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/3 18:06
 * @Version V1.0
 * @Description :
 */
public class Article implements Parcelable{

    private int id;

    private int topicId;

    private int newsTypeId;

    private String articleId;

    private String publishTime;

    private String articleUrl;

    private String imgUrl;

    private String title;

    private String desc;

    public Article() {}

    protected Article(Parcel in) {
        id = in.readInt();
        topicId = in.readInt();
        newsTypeId = in.readInt();
        articleId = in.readString();
        publishTime = in.readString();
        articleUrl = in.readString();
        imgUrl = in.readString();
        title = in.readString();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(topicId);
        dest.writeInt(newsTypeId);
        dest.writeString(articleId);
        dest.writeString(publishTime);
        dest.writeString(articleUrl);
        dest.writeString(imgUrl);
        dest.writeString(title);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(int newsTypeId) {
        this.newsTypeId = newsTypeId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Article{" +
                "topicId=" + topicId +
                ", newsTypeId=" + newsTypeId +
                ", articleId='" + articleId + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
