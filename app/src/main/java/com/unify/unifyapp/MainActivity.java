package com.unify.unifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.unify.avanza.Unify;
import com.unify.avanza.anyline.IUnifyAnyLine;
import com.unify.unifyapp.anyline.ScanMrzActivity;
import com.unify.unifyapp.anyline.ScanUniversalIdActivity;

import at.nineyards.anyline.core.LicenseException;
import io.anyline.AnylineSDK;

public class MainActivity extends AppCompatActivity implements IUnifyAnyLine {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://10.0.2.2:3000/unify_json");
        Unify.getInstance().initialize(this, this, bundle);

    }

    @Override
    public void onStartPassportScanning() {
        try {
            AnylineSDK.init(getString(R.string.anyline_license_key), this);
        } catch (LicenseException e) {

        }
        startActivity(new Intent(this, ScanMrzActivity.class));
    }

    @Override
    public void onStartUniversalIdScanning() {
        try {
            AnylineSDK.init(getString(R.string.anyline_license_key), this);
        } catch (LicenseException e) {

        }
        startActivity(new Intent(this, ScanUniversalIdActivity.class));
    }
}