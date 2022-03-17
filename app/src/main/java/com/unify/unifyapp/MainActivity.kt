package com.unify.unifyapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.unify.avanza.EventKeyValue
import com.unify.avanza.UnifyRuntimePermission
import com.unify.avanza.app.Unify
import com.unify.avanza.services.network.IUnifyInterceptor
import com.unify.avanza.services.network.IUnifyNetworkService
import es.dmoral.prefs.Prefs
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), IUnifyNetworkService {

    var url: String = ""
    lateinit var et: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        url = Prefs.with(this).read("url","");
        et = findViewById<EditText>(R.id.url)
        et.setText(url)

    }


    override fun addInterceptors(): List<Map<String, IUnifyInterceptor>> {
        val interceptors: MutableList<Map<String, IUnifyInterceptor>> = ArrayList()
        val aesInter: MutableMap<String, IUnifyInterceptor> = HashMap()
        aesInter["aes"] = AESInterceptor
        interceptors.add(aesInter)
        return interceptors
    }

    override fun getValueAgainstBindingKey(): List<String> {
        return listOf("expire", "hideProgress")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventKeyValue) {
        Log.d("Received in host app", event.bindingKey + " : " + event.value)
    }


    fun initializeSDK(view: View) {
        Prefs.with(view.context).write("url", et.text.toString());

        UnifyRuntimePermission.getPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                object : UnifyRuntimePermission.UnifyPermissionGrantedResponse {
                    override fun onUnifyPermissionGranted() {
                        val dataFromHostapp: MutableMap<String, Any> = HashMap();
                        dataFromHostapp["CPR"] = "00700" // this value provide by host app
                        Unify.getInstance().initializeSDK(this@MainActivity,
                                et.text.toString(),
                                true,
                                dataFromHostapp,
                                null,
                                this@MainActivity)
                        /*Unify.getInstance().initializeSDK(this@MainActivity,
                            "http://mdu-site1.avanzasolutions.com:8010/unifyjsons/Pre_Sales_Onboarding_Master.json",
                            true,
                            dataFromHostapp,
                            null,
                            this@MainActivity)*/
                    }

                })
    }

    fun launchSDK(view: View) {
        UnifyRuntimePermission.getPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                object : UnifyRuntimePermission.UnifyPermissionGrantedResponse {
                    override fun onUnifyPermissionGranted() {
                        Unify.getInstance().launchSDK(this@MainActivity, null);
                    }

                })

    }

}