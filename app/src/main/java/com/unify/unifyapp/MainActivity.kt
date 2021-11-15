package com.unify.unifyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unify.avanza.anyline.IDocumentScanner
import com.unify.avanza.app.Unify
import com.unify.avanza.services.network.IUnifyInterceptor
import com.unify.avanza.services.network.IUnifyNetworkService
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), IDocumentScanner, IUnifyNetworkService {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = Bundle()
//        bundle.putString("url", "http://10.0.2.2:3000/unify_json")
//        bundle.putString("url", "http://192.168.100.158:3000/unify_json")
//        bundle.putString("url", "https://run.mocky.io/v3/f1a00d07-1a9f-4da9-a65d-e74d072b14ef")
        bundle.putString("url", "http://mdu-site1.avanzasolutions.com:8010/unifyjsons/presalesDemo.json")
        Unify.getInstance().initialize(this, null, this, this, bundle)
    }

    override fun onStartPassportScanning() {

    }

    override fun onStartUniversalIdScanning() {

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