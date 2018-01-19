package com.testapp.pepoauth.rxjwithmvvpsample;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by 598653 on 18/01/18.
 */

public interface MainContract {

    interface View{

    }
    interface Presenter{
        void onClick(Observer<String> observer);
    }
}
