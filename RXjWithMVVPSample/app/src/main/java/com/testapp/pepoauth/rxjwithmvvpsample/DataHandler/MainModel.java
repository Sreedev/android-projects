package com.testapp.pepoauth.rxjwithmvvpsample.DataHandler;

import io.reactivex.Observable;

/**
 * Created by 598653 on 18/01/18.
 */

public class MainModel {

    public MainModel() {

    }

    public Observable<String> getObservable() {
        return Observable.just("Cricket", "Football");
    }

}
