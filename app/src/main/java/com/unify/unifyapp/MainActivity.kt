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
        UnifyRuntimePermission.getPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, object : UnifyRuntimePermission.UnifyPermissionGrantedResponse {
            override fun onUnifyPermissionGranted() {
                val dataFromHostapp: MutableMap<String, Any> = HashMap();
                dataFromHostapp["CPR"] = "00700" // this value provide by host app
//            Unify.getInstance().initializeSDK(this, "http://10.0.2.2:3000/unify_json", dataFromHostapp, null, this)
//            Unify.getInstance().initializeSDK(this, "http://172.16.2.229:3000/unify_json", dataFromHostapp, null, this)
                Unify.getInstance().initializeSDK(this@MainActivity, "http://mdu-site1.avanzasolutions.com:8010/unifyjsons/presales_new_demo.json", dataFromHostapp, null, this@MainActivity)
            }

        })
    }

    fun launchSDK(view: View) {
        UnifyRuntimePermission.getPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, object : UnifyRuntimePermission.UnifyPermissionGrantedResponse {
            override fun onUnifyPermissionGranted() {
                Unify.getInstance().launchSDK(this@MainActivity, null);
            }

        })

    }

}