package com.example.root.movie.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 16-7-24.
 */
public class RateInfo extends BaseInfo implements Rated ,Parcelable{
    private int rating;
    @Override
    public int getRate() {
        return rating;
    }

    @Override
    public void setRate(int rate) {
        this.rating = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rating);
    }

    public RateInfo() {
    }

    protected RateInfo(Parcel in) {
        this.rating = in.readInt();
    }

    public static final Creator<RateInfo> CREATOR = new Creator<RateInfo>() {
        public RateInfo createFromParcel(Parcel source) {
            return new RateInfo(source);
        }

        public RateInfo[] newArray(int size) {
            return new RateInfo[size];
        }
    };
}
