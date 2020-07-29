package com.zmj.viewmodelpractice.entry

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */

@Entity
data class User(var firstName: String,var lastName: String,var age: Int){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Entity
data class Book(var name: String,var pages: Int,var author: String){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}


