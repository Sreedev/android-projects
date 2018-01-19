package com.testapp.pepoauth.rxjwithmvvpsample;

import com.testapp.pepoauth.rxjwithmvvpsample.DaggerHandler.ComponentClass;
import com.testapp.pepoauth.rxjwithmvvpsample.DaggerHandler.DaggerComponentClass;
import com.testapp.pepoauth.rxjwithmvvpsample.DaggerHandler.ModuleClass;
import com.testapp.pepoauth.rxjwithmvvpsample.DataHandler.MainModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 598653 on 18/01/18.
 */

public class MainViewModel implements MainContract.Presenter {

    MainModel model;
    ComponentClass component;

    public MainViewModel() {
        component = DaggerComponentClass.builder().moduleClass(new ModuleClass()).build();

        model=component.provideMainModel();
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
