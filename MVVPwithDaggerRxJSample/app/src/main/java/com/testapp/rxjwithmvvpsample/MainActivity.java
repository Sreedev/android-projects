package com.testapp.rxjwithmvvpsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    MainViewModel mainPresenter;

    Button bFetchFromJust;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bFetchFromJust = findViewById(R.id.button_fetch_just);
        tvResult = findViewById(R.id.tv_result);

        mainPresenter = new MainViewModel();

        bFetchFromJust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                mainPresenter.onClick(observer);
            }
        });

    }


}
