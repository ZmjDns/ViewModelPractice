package com.zmj.viewmodelpractice.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.work.*
import com.zmj.viewmodelpractice.R
import com.zmj.viewmodelpractice.databinding.ActL3WorkBinding
import com.zmj.viewmodelpractice.works.SimpleWork
import java.util.concurrent.TimeUnit

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
class L3WorkAct: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActL3WorkBinding = DataBindingUtil.setContentView(this,R.layout.act_l3_work)

        binding.setOneTimeWork {
            val request = OneTimeWorkRequest.Builder(SimpleWork::class.java)
                .setInitialDelay(10,TimeUnit.SECONDS)   //设置延迟执行的时间
                .addTag("simple")   //添加标签，可以通过标签取消work
                //在后台任务返回Result.retry()的时候会回调该方法，表示随后10s后再次执行
                .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(this).enqueue(request)
            //取消
            //WorkManager.getInstance(this).cancelAllWorkByTag("simple")

            //对work的执行结果进行处理
            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(request.id)
                .observe(this) { workInfo: WorkInfo? ->
                    if (workInfo?.state == WorkInfo.State.SUCCEEDED){
                        Log.d("L3WorkAct","do work succeed")
                    }else if (workInfo?.state == WorkInfo.State.FAILED){
                        Log.d("L3WorkAct","do work failed")
                    }
                }
        }

        binding.setOneTimeWork {
            //频率未最小15分钟
            val periodicWork = PeriodicWorkRequest.Builder(SimpleWork::class.java,15,TimeUnit.MINUTES).build()
            WorkManager.getInstance(this).enqueue(periodicWork)
        }
    }

    private fun dealAFewWork(){
        //假设三个Work要依次执行
        val syncWork = OneTimeWorkRequest.Builder(SimpleWork::class.java).build()
        val compressWork = OneTimeWorkRequest.Builder(SimpleWork::class.java).build()
        val uploadWork = OneTimeWorkRequest.Builder(SimpleWork::class.java).build()

        WorkManager.getInstance(this)
            .beginWith(syncWork)
            .then(compressWork)
            .then(uploadWork)
            .enqueue()
    }
}