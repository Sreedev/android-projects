package com.testapp.rxjwithmvvpsample;

import com.testapp.rxjwithmvvpsample.DaggerHandler.ComponentClass;
import com.testapp.rxjwithmvvpsample.DaggerHandler.DaggerComponentClass;
import com.testapp.rxjwithmvvpsample.DaggerHandler.ModuleClass;
import com.testapp.rxjwithmvvpsample.DataHandler.MainModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * This is the ViewModel or the presenter. I am naming as ViewModel as we are trying to implement MVVP here.
 *
 * Created by Sreedev on 18/01/18.
 */

public class MainViewModel implements MainContract.ViewModel {

    MainModel model;
    ComponentClass component;

    public MainViewModel() {

        //The component is been initialize.Dagger<Your_component_class_name>, this is the
        // format to initialize the component class. If you have any issues in the component class name
        // it will show a compilation error.
        component = DaggerComponentClass.builder().moduleClass(new ModuleClass()).build();

        //Injecting singleton object
        model = component.provideMainModel();
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
