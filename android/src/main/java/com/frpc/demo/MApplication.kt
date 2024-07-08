package com.frpc.demo

import android.app.Application
import com.frpc.common.AndroidBaseEnv

class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidBaseEnv.mContext = this
    }
}