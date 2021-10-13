package com.unify.unifyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.unify.avanza.anyline.IUnifyAnyLine;
import com.unify.avanza.app.Unify;
import com.unify.avanza.services.network.ApiClient;
import com.unify.avanza.services.network.IUnifyInterceptor;
import com.unify.avanza.services.network.IUnifyNetworkService;
import com.unify.unifyapp.anyline.ScanMrzActivity;
import com.unify.unifyapp.anyline.ScanUniversalIdActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.nineyards.anyline.core.LicenseException;
import io.anyline.AnylineSDK;

public class MainActivity extends AppCompatActivity implements IUnifyAnyLine, IUnifyNetworkService{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
//        bundle.putString("url", "https://run.mocky.io/v3/d070b8e2-1c4d-4b65-96d4-82d9f13d383a");
        bundle.putString("url", "http://10.0.2.2:3000/unify_json");
        Unify.getInstance().initialize(this, null, this, this, bundle);

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

    @Override
    public List<Map<String, IUnifyInterceptor>> addInterceptors() {
        List<Map<String, IUnifyInterceptor>> interceptors = new ArrayList<>();
        Map<String, IUnifyInterceptor> aesInter = new HashMap<>();
        aesInter.put("aes",AESInterceptor.getInstance());
        aesInter.put("checksum",CheckSumInterceptor.getInstance());
        interceptors.add(aesInter);
        return interceptors;
    }
}