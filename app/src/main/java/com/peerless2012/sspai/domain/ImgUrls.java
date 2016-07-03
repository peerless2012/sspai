package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/3 15:40
 * @Version V1.0
 * @Description :
 */
public class ImgUrls implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("origin_url")
    @Expose
    private String originUrl;

    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;

    @SerializedName("thumb_url_320")
    @Expose
    private String thumbUrl320;

    @SerializedName("thumb_url_640")
    @Expose
    private String thumbUrl640;

    @SerializedName("thumb_url_800")
    @Expose
    private String thumbUrl800;

    public ImgUrls() {
    }

    protected ImgUrls(Parcel in) {
        id = in.readString();
        originUrl = in.readString();
        thumbUrl = in.readString();
        thumbUrl320 = in.readString();
        thumbUrl640 = in.readString();
        thumbUrl800 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(originUrl);
        dest.writeString(thumbUrl);
        dest.writeString(thumbUrl320);
        dest.writeString(thumbUrl640);
        dest.writeString(thumbUrl800);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImgUrls> CREATOR = new Creator<ImgUrls>() {
        @Override
        public ImgUrls createFromParcel(Parcel in) {
            return new ImgUrls(in);
        }

        @Override
        public ImgUrls[] newArray(int size) {
            return new ImgUrls[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrl320() {
        return thumbUrl320;
    }

    public void setThumbUrl320(String thumbUrl320) {
        this.thumbUrl320 = thumbUrl320;
    }

    public String getThumbUrl640() {
        return thumbUrl640;
    }

    public void setThumbUrl640(String thumbUrl640) {
        this.thumbUrl640 = thumbUrl640;
    }

    public String getThumbUrl800() {
        return thumbUrl800;
    }

    public void setThumbUrl800(String thumbUrl800) {
        this.thumbUrl800 = thumbUrl800;
    }

    @Override
    public String toString() {
        return "ImgUrls{" +
                "id='" + id + '\'' +
                ", originUrl='" + originUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", thumbUrl320='" + thumbUrl320 + '\'' +
                ", thumbUrl640='" + thumbUrl640 + '\'' +
                ", thumbUrl800='" + thumbUrl800 + '\'' +
                '}';
    }
}
