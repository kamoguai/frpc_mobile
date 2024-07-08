package com.frpc.common

import android.content.Context

object AndroidBaseEnv {

    var mContext: Context? = null
        set(value) {
            field = value!!.applicationContext
        }

    fun context(): Context = mContext!!

}