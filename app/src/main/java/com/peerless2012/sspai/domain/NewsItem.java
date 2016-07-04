package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/29 22:29
 * @Version V1.0
 * @Description :
 */
public class NewsItem implements Parcelable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("title")
    @Expose
    private String newsTitle;

    @SerializedName("description")
    @Expose
    private String newsDescription;


    @SerializedName("views")
    @Expose
    private int views;


    @SerializedName("published")
    @Expose
    private long publishedTime;

    @SerializedName("created")
    @Expose
    private long createdTime;

    @SerializedName("modified")
    @Expose
    private long modifiedTime;

    @SerializedName("like_count")
    @Expose
    private int likeCount;

    @SerializedName("comment_count")
    @Expose
    private int commentCount;

    @SerializedName("favorite_count")
    @Expose
    private int favoriteCount;

    @SerializedName("banner")
    @Expose
    private List<ImgUrls> imgUrlses;

    @SerializedName("user")
    @Expose
    private List<Author> authors;

    @SerializedName("content")
    @Expose
    private String content;

    protected NewsItem(Parcel in) {
        id = in.readInt();
        url = in.readString();
        newsTitle = in.readString();
        newsDescription = in.readString();
        views = in.readInt();
        publishedTime = in.readLong();
        createdTime = in.readLong();
        modifiedTime = in.readLong();
        likeCount = in.readInt();
        commentCount = in.readInt();
        favoriteCount = in.readInt();
        imgUrlses = in.createTypedArrayList(ImgUrls.CREATOR);
        authors = in.createTypedArrayList(Author.CREATOR);
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(newsTitle);
        dest.writeString(newsDescription);
        dest.writeInt(views);
        dest.writeLong(publishedTime);
        dest.writeLong(createdTime);
        dest.writeLong(modifiedTime);
        dest.writeInt(likeCount);
        dest.writeInt(commentCount);
        dest.writeInt(favoriteCount);
        dest.writeTypedList(imgUrlses);
        dest.writeTypedList(authors);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(long publishedTime) {
        this.publishedTime = publishedTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public List<ImgUrls> getImgUrlses() {
        return imgUrlses;
    }

    public void setImgUrlses(List<ImgUrls> imgUrlses) {
        this.imgUrlses = imgUrlses;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
