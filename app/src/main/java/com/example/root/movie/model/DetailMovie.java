package com.example.root.movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *  data get by movie id
 */
public class DetailMovie implements Parcelable{


    /**
     * adult : false
     * backdropPath : /hNFMawyNDWZKKHU4GYCBz1krsRM.jpg
     * belongs_to_collection : null
     * budget : 63000000
     * genres : [{"id":18,"name":"Drama"}]
     * homepage :
     * id : 550
     * imdbId : tt0137523
     * originalLanguage : en
     * originalTitle : Fight Club
     * overview : A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.
     * popularity : 2.50307202280779
     * posterPath : /2lECpi35Hnbpa4y46JX0aY3AWTy.jpg
     * production_companies : [{"name":"20th Century Fox","id":25},{"name":"Fox 2000 Pictures","id":711},{"name":"Regency Enterprises","id":508}]
     * production_countries : [{"iso_3166_1":"DE","name":"Germany"},{"iso_3166_1":"US","name":"United States of America"}]
     * releaseDate : 1999-10-14
     * revenue : 100853753
     * runtime : 139
     * spoken_languages : [{"iso_639_1":"en","name":"English"}]
     * status : Released
     * tagline : How much can you know about yourself if you've never been in a fight?
     * title : Fight Club
     * video : false
     * voteAverage : 7.7
     * voteCount : 3185
     */

    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private String overview;
    private double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    private int runtime;
    private String title;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdropPath);
        dest.writeString(this.imdbId);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeDouble(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.runtime);
        dest.writeString(this.title);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.voteCount);
    }

    public DetailMovie() {
    }

    protected DetailMovie(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdropPath = in.readString();
        this.imdbId = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.runtime = in.readInt();
        this.title = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readInt();
    }

    public static final Creator<DetailMovie> CREATOR = new Creator<DetailMovie>() {
        public DetailMovie createFromParcel(Parcel source) {
            return new DetailMovie(source);
        }

        public DetailMovie[] newArray(int size) {
            return new DetailMovie[size];
        }
    };
}
