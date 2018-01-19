package com.testapp.pepoauth.rxjwithmvvpsample.DaggerHandler;

import com.testapp.pepoauth.rxjwithmvvpsample.DataHandler.MainModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sreedev on 19/01/18.
 *
 * This is a class created for Dagger to work. This will be the module class that provides all the dependencies.
 */

@Module
public class ModuleClass {

    /**
     * provideMainModel - Provides the singleton object of MainModel class
     *
     * @return MainModel class object
     */
    @Provides
    @Singleton
    MainModel provideMainModel() {
        return new MainModel();
    }

}
