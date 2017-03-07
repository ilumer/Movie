package com.example.root.movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieData {
    private int page;

    private List<MovieInfo> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieInfo> getResults() {
        return results;
    }

    public void setResults(List<MovieInfo> results) {
        this.results = results;
    }

}
