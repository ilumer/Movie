package com.example.root.movie.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 16-7-22.
 * 用户数据返回的Movie Tv的基本类
 */
public class BaseInfo implements Parcelable{

    /**
     * backdrop_path : /xetk5vdoKxMIto5O5boqPGxWnoA.jpg
     * id : 76726
     * original_title : Chronicle
     * release_date : 2012-02-03
     * poster_path : /pSYRHYvOd8aFaddVUdyXJGZ8F6W.jpg
     * title : Chronicle
     * vote_average : 7.3
     * vote_count : 32
     */

    private String backdrop_path;
    private int id;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String title;
    private double vote_average;
    private int vote_count;

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdrop_path);
        dest.writeInt(this.id);
        dest.writeString(this.original_title);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeString(this.title);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
    }

    public BaseInfo() {
    }

    protected BaseInfo(Parcel in) {
        this.backdrop_path = in.readString();
        this.id = in.readInt();
        this.original_title = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.title = in.readString();
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
    }

    public static final Creator<BaseInfo> CREATOR = new Creator<BaseInfo>() {
        public BaseInfo createFromParcel(Parcel source) {
            return new BaseInfo(source);
        }

        public BaseInfo[] newArray(int size) {
            return new BaseInfo[size];
        }
    };
}
