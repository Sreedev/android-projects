package com.testapp.pepoauth.rxjwithmvvpsample;

import io.reactivex.Observable;

/**
 * Created by 598653 on 18/01/18.
 */

public class MainModel {

    private static MainModel mModel;

    private MainModel() {

    }

    public static MainModel getInstance() {
        if (mModel == null)
            mModel = new MainModel();

        return mModel;
    }

    public Observable<String> getObservable() {
        return Observable.just("Cricket", "Football");
    }

}
