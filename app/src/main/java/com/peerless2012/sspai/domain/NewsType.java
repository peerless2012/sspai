package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/29 22:27
 * @Version V1.0
 * @Description :
 */
public class NewsType implements Parcelable{

    @SerializedName("typeId")
    @Expose
    private int typeId;

    @SerializedName("typeName")
    @Expose
    private String typeName;

    @SerializedName("newsTag")
    @Expose
    private String newsTag;

    @SerializedName("topicTag")
    @Expose
    private String topicTag;

    @SerializedName("topicId")
    @Expose
    private int topicId;

    protected NewsType(Parcel in) {
        typeId = in.readInt();
        typeName = in.readString();
        newsTag = in.readString();
        topicTag = in.readString();
        topicId = in.readInt();
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNewsTag() {
        return newsTag;
    }

    public void setNewsTag(String newsTag) {
        this.newsTag = newsTag;
    }

    public String getTopicTag() {
        return topicTag;
    }

    public void setTopicTag(String topicTag) {
        this.topicTag = topicTag;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(typeId);
        dest.writeString(typeName);
        dest.writeString(newsTag);
        dest.writeString(topicTag);
        dest.writeInt(topicId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsType> CREATOR = new Creator<NewsType>() {
        @Override
        public NewsType createFromParcel(Parcel in) {
            return new NewsType(in);
        }

        @Override
        public NewsType[] newArray(int size) {
            return new NewsType[size];
        }
    };


}
