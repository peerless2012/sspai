package com.peerless2012.sspai.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/29 22:29
 * @Version V1.0
 * @Description :
 */
public class NewsInfo {

    @SerializedName("typeId")
    @Expose
    private int id;

    @SerializedName("typeId")
    @Expose
    private String newsTitle;

    @SerializedName("typeId")
    @Expose
    private String newsSubTitle;

    @SerializedName("typeId")
    @Expose
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSubTitle() {
        return newsSubTitle;
    }

    public void setNewsSubTitle(String newsSubTitle) {
        this.newsSubTitle = newsSubTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "id=" + id +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsSubTitle='" + newsSubTitle + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
