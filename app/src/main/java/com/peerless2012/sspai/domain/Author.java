package com.peerless2012.sspai.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/3 15:48
 * @Version V1.0
 * @Description :
 */
public class Author implements Parcelable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nikename")
    @Expose
    private String nickName;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("avatar")
    @Expose
    private String avatar;


    protected Author(Parcel in) {
        id = in.readInt();
        nickName = in.readString();
        bio = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nickName);
        dest.writeString(bio);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", bio='" + bio + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
