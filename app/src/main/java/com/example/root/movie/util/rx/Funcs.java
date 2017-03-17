package com.example.root.movie.util.rx;

import rx.functions.Func1;

/**
 * Created by ilumer on 17-3-16.
 */

public class Funcs {

    private Funcs(){
        throw new AssertionError("no instance");
    }

    public static <T> Func1<T,Boolean> not(final Func1<T,Boolean> func){
        return new Func1<T, Boolean>() {
            @Override
            public Boolean call(T t) {
                return !func.call(t);
            }
        };
    }
}
