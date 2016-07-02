package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/29 22:26
 * @Version V1.0
 * @Description :
 */
public class Topic implements Parcelable{

    @SerializedName("topicId")
    @Expose
    private int topicId;

    @SerializedName("topicName")
    @Expose
    private String topicName;

    @SerializedName("topicTag")
    @Expose
    private String topicTag;

    @SerializedName("newsTypes")
    @Expose
    private List<NewsType> newsTypes;

    protected Topic(Parcel in) {
        topicId = in.readInt();
        topicName = in.readString();
        topicTag = in.readString();
    }

    public Topic() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(topicId);
        dest.writeString(topicName);
        dest.writeString(topicTag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public List<NewsType> getNewsTypes() {
        return newsTypes;
    }

    public void setNewsTypes(List<NewsType> newsTypes) {
        this.newsTypes = newsTypes;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicTag() {
        return topicTag;
    }

    public void setTopicTag(String topicTag) {
        this.topicTag = topicTag;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", newsTypes=" + newsTypes +
                '}';
    }
}
