package com.sdev.sampledevdagger2.DaggerHelper;

import com.sdev.sampledevdagger2.DataHelper.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sreedev on 19/01/18.
 *
 * This is the module class for Dagger2 to work. This class will contain all the singleton class that
 * will provide the dependencies. Its acts like a stock room of dependency objects.
 */

@Module
public class MainDaggerModule {

    /**
     * This provides the singleton object of Utils class
     * @return Utils class singleton object
     */
    @Provides
    @Singleton
    Utils provideUtils() {
        return new Utils();
    }
}
