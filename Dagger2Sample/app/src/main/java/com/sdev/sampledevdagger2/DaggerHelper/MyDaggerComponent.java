package com.sdev.sampledevdagger2.DaggerHelper;

import com.sdev.sampledevdagger2.DataHelper.Utils;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 *
 * Created by Sreedev on 19/01/18.
 */

@Singleton
@Component(modules = {MainDaggerModule.class})
public interface MyDaggerComponent {

    Utils provideUtils();
}
