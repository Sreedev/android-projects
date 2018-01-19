package com.testapp.rxjwithmvvpsample.DataHandler;

import io.reactivex.Observable;

/**
 * Created by Sreedev on 18/01/18.
 * <p>
 * This is the main model class
 */

public class MainModel {

    /**
     * Constructor class for MainModel
     */
    public MainModel() {

    }

    /**
     * Using Rxjava this method returns a set of String. Just operator of Rxjava
     * is been used in this method to keep it simple. This method can be customized according to the
     * requirement.
     *
     * @return
     */
    public Observable<String> getObservable() {
        return Observable.just("Cricket", "Football");
    }

}
