package com.sdev.sampledevdagger2;

import android.app.Activity;
import android.os.Bundle;

/**
 * This Activity acts as the view in MVP and it implements View from the contract
 */
public class MainActivity extends Activity implements MainContract.View {
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //presenter is initialized
        presenter = new MainPresenter(this);

        //Calling the method in presenter
        presenter.addNumbers(1, 2);

    }

    @Override
    public void result(int result) {
        System.out.println("Result = " + result);
    }
}
