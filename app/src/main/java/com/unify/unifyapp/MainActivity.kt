package com.unify.unifyapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.unify.avanza.EventKeyValue
import com.unify.avanza.app.Unify
import com.unify.avanza.services.network.IUnifyInterceptor
import com.unify.avanza.services.network.IUnifyNetworkService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), IUnifyNetworkService {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = Bundle()
//        bundle.putString("url", "http://10.0.2.2:3000/unify_json")
        bundle.putString("url", "http://mdu-site1.avanzasolutions.com:8010/unifyjsons/eKYC.json")
        Unify.getInstance().initialize(this, null, this, bundle)
        EventBus.getDefault().register(this)


    }


    override fun addInterceptors(): List<Map<String, IUnifyInterceptor>> {
        val interceptors: MutableList<Map<String, IUnifyInterceptor>> = ArrayList()
        val aesInter: MutableMap<String, IUnifyInterceptor> = HashMap()
        aesInter["aes"] = AESInterceptor
        aesInter["checksum"] = CheckSumInterceptor
        interceptors.add(aesInter)
        return interceptors
    }

    override fun getValueAgainstBindingKey(): List<String> {
        return listOf("usCitizenSwitch")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventKeyValue) {
        Log.d("Received in host app", event.bindingKey + " : " + event.value)
    }
}