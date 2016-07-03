package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/3 13:18
 * @Version V1.0
 * @Description :
 */
public class News implements Parcelable{

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("per_page")
    @Expose
    private int perPage;

    @SerializedName("current_page")
    @Expose
    private int currentPage;

    @SerializedName("last_page")
    @Expose
    private int lastPage;

    @SerializedName("from")
    @Expose
    private int from;

    @SerializedName("to")
    @Expose
    private int to;

    @SerializedName("data")
    @Expose
    private List<NewsItem> newsItems;


    protected News(Parcel in) {
        total = in.readInt();
        perPage = in.readInt();
        currentPage = in.readInt();
        lastPage = in.readInt();
        from = in.readInt();
        to = in.readInt();
        newsItems = in.createTypedArrayList(NewsItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeInt(perPage);
        dest.writeInt(currentPage);
        dest.writeInt(lastPage);
        dest.writeInt(from);
        dest.writeInt(to);
        dest.writeTypedList(newsItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    @Override
    public String toString() {
        return "News{" +
                "total=" + total +
                ", perPage=" + perPage +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                ", from=" + from +
                ", to=" + to +
                ", newsItems=" + newsItems +
                '}';
    }
}
