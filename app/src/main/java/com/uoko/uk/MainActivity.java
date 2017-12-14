package com.uoko.uk;

import android.content.Intent;
import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.uoko.uk.ui.activity.TestActivity;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in = new Intent(this, TestActivity.class);
        startActivity(in);


    }
}
