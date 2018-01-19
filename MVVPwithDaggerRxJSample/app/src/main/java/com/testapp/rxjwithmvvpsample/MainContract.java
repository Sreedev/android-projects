package com.testapp.rxjwithmvvpsample;

import io.reactivex.Observer;

/**
 * This is the contracts class that interacts between View and ViewModel(ViewModel)
 *
 * Created by Sreedev on 18/01/18.
 */

public interface MainContract {

    /**
     * Interface for view. That means for Activity, fragments or any other custom view classes.
     */
    interface View {
    }

    /**
     * Interface for presenter or ViewModel. That is for any other classes which acts as view model.
     */
    interface ViewModel {

        /**
         * When the user clicks on the button view will call this method which will be reaching the presenter
         *
         * @param observer Observer holds observe the data from the which ever model class
         */
        void onClick(Observer<String> observer);
    }
}
