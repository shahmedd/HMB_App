package com.unify.unifyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.nineyards.anyline.core.LicenseException
import com.unify.avanza.anyline.IUnifyAnyLine
import com.unify.avanza.app.Unify
import com.unify.avanza.services.network.IUnifyInterceptor
import com.unify.avanza.services.network.IUnifyNetworkService
import com.unify.unifyapp.anyline.ScanMrzActivity
import com.unify.unifyapp.anyline.ScanUniversalIdActivity
import io.anyline.AnylineSDK
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), IUnifyAnyLine, IUnifyNetworkService {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = Bundle()
        bundle.putString("url", "http://10.0.2.2:3000/unify_json")
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