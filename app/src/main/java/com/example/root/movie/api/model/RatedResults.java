package com.example.root.movie.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-7-24.
 * 推荐返回的数据类型
 */
public class RatedResults {
    private int page;
    private int total_pages;
    private int total_results;
    private List<RateInfo> results;

    public List<RateInfo> getmList() {
        return results;
    }

    public void setmList(List<RateInfo> mList) {
        this.results = mList;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
