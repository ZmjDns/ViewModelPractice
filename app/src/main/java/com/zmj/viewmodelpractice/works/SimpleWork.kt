package com.zmj.viewmodelpractice.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
class SimpleWork(context: Context,params: WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {
        Log.d("SimpleWork","do background work in SimpleWork")
        return Result.success()
    }
}