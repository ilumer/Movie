package com.example.root.movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieData {
    private int page;

    private List<ResultsBean> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Parcelable{
        private String poster_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private int id;
        private String original_title;
        private String original_language;
        private String title;
        private String backdrop_path;
        private int width;
        private int height;
        private double popularity;
        private int vote_count;
        private boolean video;
        private double vote_average;

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.poster_path);
            dest.writeByte(adult ? (byte) 1 : (byte) 0);
            dest.writeString(this.overview);
            dest.writeString(this.release_date);
            dest.writeInt(this.id);
            dest.writeString(this.original_title);
            dest.writeString(this.original_language);
            dest.writeString(this.title);
            dest.writeString(this.backdrop_path);
            dest.writeInt(this.width);
            dest.writeInt(this.height);
            dest.writeDouble(this.popularity);
            dest.writeInt(this.vote_count);
            dest.writeByte(video ? (byte) 1 : (byte) 0);
            dest.writeDouble(this.vote_average);
        }

        public ResultsBean() {
        }

        protected ResultsBean(Parcel in) {
            this.poster_path = in.readString();
            this.adult = in.readByte() != 0;
            this.overview = in.readString();
            this.release_date = in.readString();
            this.id = in.readInt();
            this.original_title = in.readString();
            this.original_language = in.readString();
            this.title = in.readString();
            this.backdrop_path = in.readString();
            this.width = in.readInt();
            this.height = in.readInt();
            this.popularity = in.readDouble();
            this.vote_count = in.readInt();
            this.video = in.readByte() != 0;
            this.vote_average = in.readDouble();
        }

        public static final Creator<ResultsBean> CREATOR = new Creator<ResultsBean>() {
            public ResultsBean createFromParcel(Parcel source) {
                return new ResultsBean(source);
            }

            public ResultsBean[] newArray(int size) {
                return new ResultsBean[size];
            }
        };
    }
}
