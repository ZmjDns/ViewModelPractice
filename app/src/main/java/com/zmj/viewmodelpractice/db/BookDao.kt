package com.zmj.viewmodelpractice.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zmj.viewmodelpractice.entry.Book

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
@Dao
interface BookDao {

    @Insert
    fun insert(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>

}