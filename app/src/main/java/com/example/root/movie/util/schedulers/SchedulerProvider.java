package com.example.root.movie.util.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ilumer on 17-3-14.
 */

public class SchedulerProvider implements BaseSchedulerProvider{
    public static volatile SchedulerProvider INSTANCE;

    private SchedulerProvider(){
    }


    public static SchedulerProvider getInstance(){
        if (INSTANCE == null){
            synchronized (SchedulerProvider.class){
                if (INSTANCE == null){
                    INSTANCE = new SchedulerProvider();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
