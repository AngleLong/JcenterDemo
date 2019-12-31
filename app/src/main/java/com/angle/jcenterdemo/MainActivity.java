package com.angle.jcenterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.angle.testlib.HelloWorld;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HelloWorld.say();

    }
}
