package com.example.root.movie.util;

import com.example.root.movie.util.schedulers.BaseSchedulerProvider;
import com.example.root.movie.util.schedulers.SchedulerProvider;

/**
 * Created by ilumer on 17-3-14.
 */

public class Injection {

    public static BaseSchedulerProvider provideSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }
}
