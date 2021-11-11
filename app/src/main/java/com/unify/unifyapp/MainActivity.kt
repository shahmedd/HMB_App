package com.unify.unifyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.nineyards.anyline.core.LicenseException
import com.unify.avanza.anyline.IDocumentScanner
import com.unify.avanza.app.Unify
import com.unify.avanza.services.network.IUnifyInterceptor
import com.unify.avanza.services.network.IUnifyNetworkService
import com.unify.unifyapp.anyline.ScanMrzActivity
import com.unify.unifyapp.anyline.ScanUniversalIdActivity
import io.anyline.AnylineSDK
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), IDocumentScanner, IUnifyNetworkService {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = Bundle()
        bundle.putString("url", "http://10.0.2.2:3000/unify_json")
//        bundle.putString("url", "http://192.168.100.158:3000/unify_json")
//        bundle.putString("url", "https://run.mocky.io/v3/f1a00d07-1a9f-4da9-a65d-e74d072b14ef")
        Unify.getInstance().initialize(this, null, this, this, bundle)
    }

    override fun onStartPassportScanning() {
        try {
            AnylineSDK.init(getString(R.string.anyline_license_key), this)
        } catch (e: LicenseException) {
        }
        startActivity(Intent(this, ScanMrzActivity::class.java))
    }

    override fun onStartUniversalIdScanning() {
        try {
            AnylineSDK.init(getString(R.string.anyline_license_key), this)
        } catch (e: LicenseException) {
        }
        startActivity(Intent(this, ScanUniversalIdActivity::class.java))
    }

    override fun addInterceptors(): List<Map<String, IUnifyInterceptor>> {
        val interceptors: MutableList<Map<String, IUnifyInterceptor>> = ArrayList()
        val aesInter: MutableMap<String, IUnifyInterceptor> = HashMap()
        aesInter["aes"] = AESInterceptor
        aesInter["checksum"] = CheckSumInterceptor
        interceptors.add(aesInter)
        return interceptors
    }
}