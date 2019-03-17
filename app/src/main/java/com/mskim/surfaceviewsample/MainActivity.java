package com.mskim.surfaceviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 직접 View를 넣을 수가 있다.
        // setContentView(R.layout.activity_main);
        setContentView(new MySurfaceView(this));
    }
}
