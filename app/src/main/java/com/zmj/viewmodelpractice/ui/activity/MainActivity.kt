package com.zmj.viewmodelpractice.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zmj.viewmodelpractice.R

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_main)

    }

    private fun <T>startAct(act: Class<T>){
        startActivity(Intent(this,act))
    }

    fun viewModel(view: View) {startAct(L1ViewModelAct::class.java)}
    fun roomDb(view: View) { startAct(L2RoomDbAct::class.java) }
    fun workManager(view: View) { startAct(L3WorkAct::class.java) }
}