package com.testapp.pepoauth.rxjwithmvvpsample.DaggerHandler;

import com.testapp.pepoauth.rxjwithmvvpsample.DataHandler.MainModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 598653 on 19/01/18.
 */

@Singleton
@Component(modules = {ModuleClass.class})
public interface ComponentClass {

    MainModel provideMainModel();

}
