package com.testapp.rxjwithmvvpsample.DaggerHandler;

import com.testapp.rxjwithmvvpsample.DaggerHandler.ModuleClass;
import com.testapp.rxjwithmvvpsample.DataHandler.MainModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sreedev on 19/01/18.
 *
 * This is the component class for dagger which act as the interface for module and dependent class.
 */

@Singleton
@Component(modules = {ModuleClass.class})
public interface ComponentClass {

    /**
     * This method provides the singleton object of MainModel class
     *
     * @return Main model class object
     */
    MainModel provideMainModel();

}
