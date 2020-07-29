package com.zmj.viewmodelpractice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zmj.viewmodelpractice.entry.User

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
object Repository {

    fun getUser(userId: String): LiveData<User>{
        val liveData = MutableLiveData<User>()
        liveData.value = User(userId,userId,20)
        return liveData
    }

    fun refresh(): LiveData<String>? {
        val liveData = MutableLiveData<String>()
        liveData.value = "AAA"
        return liveData
    }

}