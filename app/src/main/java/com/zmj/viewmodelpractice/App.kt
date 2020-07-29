package com.zmj.viewmodelpractice

import android.app.Application
import android.content.Context

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
class App: Application() {

    companion object{
        private lateinit var mContext: Context

        fun getAppContext(): Context{
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

}