package com.sdev.sampledevdagger2;

import com.sdev.sampledevdagger2.DaggerHelper.MainDaggerModule;
import com.sdev.sampledevdagger2.DaggerHelper.MyDaggerComponent;
import com.sdev.sampledevdagger2.DataHelper.Utils;

/**
 * This is the presenter class. which implements Presenter interface which communicates with
 * the data handling classes
 *
 * Created by Sreedev on 19/01/18.
 */

public class MainPresenter implements MainContract.Presenter {

    //Creating a object of the component class
    MyDaggerComponent component;

    //Dependency of this class
    Utils myUtils;

    MainContract.View view;

    public MainPresenter(MainContract.View view) {
        //The component is been initialize. Dagger<Your_component_class_name>, this is the
        // format to initialize the component class. if the component class is not well written.
        //It will show a compilation error
        component = DaggerMyDaggerComponent.builder().mainDaggerModule(new MainDaggerModule()).build();

        //Injecting singleton object
        myUtils = component.provideUtils();

        this.view=view;
    }

    @Override
    public void addNumbers(int a, int b) {
        view.result(myUtils.addNumbers(a, b));
    }
}
