package com.unify.unifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unify.avanza.Unify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://10.0.2.2:3000/unify_json");
        Unify.getInstance().initialize(this, null, bundle);
    }
}