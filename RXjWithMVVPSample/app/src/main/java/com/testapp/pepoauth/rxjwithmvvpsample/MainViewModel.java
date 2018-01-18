package com.testapp.pepoauth.rxjwithmvvpsample;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 598653 on 18/01/18.
 */

public class MainViewModel implements MainContract.Presenter {
    private MainModel model;


    public MainViewModel() {
        model = MainModel.getInstance();
    }


    @Override
    public void onClick(Observer<String> observer) {
        model.getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
