package com.unify.unifyapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.unify.avanza.EventKeyValue
import com.unify.avanza.UnifyRuntimePermission
import com.unify.avanza.app.Unify
import com.unify.avanza.services.network.IUnifyInterceptor
import com.unify.avanza.services.network.IUnifyNetworkService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), IUnifyNetworkService {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        return listOf("expire")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventKeyValue) {
        Log.d("Received in host app", event.bindingKey + " : " + event.value)
    }


    fun initializeSDK(view: View) {
        UnifyRuntimePermission.getInstance().getPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            val dataFromHostapp: MutableMap<String, Any> = HashMap();
            dataFromHostapp["CPR"] = "00700" // this value provide by host app
            Unify.getInstance().initializeSDK(this, "http://mdu-site1.avanzasolutions.com:8010/unifyjsons/eKYC.json", dataFromHostapp, null, this)
        }
    }

    fun launchSDK(view: View) {
        UnifyRuntimePermission.getInstance().getPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            Unify.getInstance().launchSDK(this, null);
        }
    }
}