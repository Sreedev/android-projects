package com.testapp.pepoauth.rxjwithmvvpsample.DaggerHandler;

import com.testapp.pepoauth.rxjwithmvvpsample.DataHandler.MainModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 598653 on 19/01/18.
 */

@Module
public class ModuleClass {

    @Provides
    @Singleton
    MainModel provideMainModel(){
        return new MainModel();
    }

}
