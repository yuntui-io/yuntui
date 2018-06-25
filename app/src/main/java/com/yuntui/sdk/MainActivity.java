package com.yuntui.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import io.yuntui.Yuntui;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Yuntui.shared.logEvent("@open_mainActivity", new HashMap<String, Object>());
    }
}
