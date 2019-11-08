package com.testapp.rxjwithmvvpsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Activity that implements View from the Contract class
 */

public class MainActivity extends Activity implements MainContract.View {

    MainViewModel mainViewModel;

    Button bFetchFromJust;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bFetchFromJust = findViewById(R.id.button_fetch_just);
        tvResult = findViewById(R.id.tv_result);

        mainViewModel = new MainViewModel();

        bFetchFromJust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating an observer which will observe on the observable for the responses
                //this observer will be passed to ViewModel for further processing
                Observer<String> observer = new Observer<String>() {
                    String s = "";

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        System.out.println(value);
                        s = s + " " + value;
                        tvResult.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                };

                //Passing observer to the ViewModel
                mainViewModel.onClick(observer);
            }
        });

    }


}
