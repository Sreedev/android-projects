package com.testapp.pepoauth.rxjwithmvvpsample;

/**
 * Created by 598653 on 18/01/18.
 */

public class Utils {

    private static Utils utils;

    private Utils() {
    }

    public static Utils getInstance(){
        if(utils==null)
            utils=new Utils();

        return utils;
    }

    public void getCurrentWeather(){

    }
}
