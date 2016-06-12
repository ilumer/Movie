package com.example.root.movie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  data get by movie id
 */
public class DetialMovie implements Parcelable{


    /**
     * adult : false
     * backdrop_path : /hNFMawyNDWZKKHU4GYCBz1krsRM.jpg
     * belongs_to_collection : null
     * budget : 63000000
     * genres : [{"id":18,"name":"Drama"}]
     * homepage :
     * id : 550
     * imdb_id : tt0137523
     * original_language : en
     * original_title : Fight Club
     * overview : A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.
     * popularity : 2.50307202280779
     * poster_path : /2lECpi35Hnbpa4y46JX0aY3AWTy.jpg
     * production_companies : [{"name":"20th Century Fox","id":25},{"name":"Fox 2000 Pictures","id":711},{"name":"Regency Enterprises","id":508}]
     * production_countries : [{"iso_3166_1":"DE","name":"Germany"},{"iso_3166_1":"US","name":"United States of America"}]
     * release_date : 1999-10-14
     * revenue : 100853753
     * runtime : 139
     * spoken_languages : [{"iso_639_1":"en","name":"English"}]
     * status : Released
     * tagline : How much can you know about yourself if you've never been in a fight?
     * title : Fight Club
     * video : false
     * vote_average : 7.7
     * vote_count : 3185
     */

    private boolean adult;
    private String backdrop_path;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private int runtime;
    private String title;
    private double vote_average;
    private int vote_count;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.imdb_id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeDouble(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeInt(this.runtime);
        dest.writeString(this.title);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
    }

    public DetialMovie() {
    }

    protected DetialMovie(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdrop_path = in.readString();
        this.imdb_id = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.runtime = in.readInt();
        this.title = in.readString();
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
    }

    public static final Creator<DetialMovie> CREATOR = new Creator<DetialMovie>() {
        public DetialMovie createFromParcel(Parcel source) {
            return new DetialMovie(source);
        }

        public DetialMovie[] newArray(int size) {
            return new DetialMovie[size];
        }
    };
}
