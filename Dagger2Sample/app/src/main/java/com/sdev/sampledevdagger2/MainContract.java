package com.sdev.sampledevdagger2;

/**
 * Created by Sreedev on 19/01/18.
 *
 * MainContract is the class that helps for communication between View and Presenter
 */

public interface MainContract {

    /**
     * View is an interface which will be implemented inView classes
     */
    interface View{
        /**
         * The result will be returned back here from the presenter
         * @param result from the presenter
         */
        void result(int result);
    }

    /**
     * Presenter is an interface that will be implemented in presenter classes
     */
    interface Presenter{

        /**
         * Takes two integers and pass it to the model class to do addition
         * @param a integer one for addition
         * @param b integer two for addition
         */
        void addNumbers(int a,int b);
    }
}
