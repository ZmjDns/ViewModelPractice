package com.zmj.viewmodelpractice.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zmj.viewmodelpractice.App
import com.zmj.viewmodelpractice.entry.Book
import com.zmj.viewmodelpractice.entry.User

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :room数据库创建和升级
 */
@Database(version = 3,entities = [User::class,Book::class])
abstract class AppDataBase : RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object{

        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table Book (id integer primary key autoincrement not null,name text not null,pages integer not null)")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table Book add column author text not null default 'unknown'")
            }
        }

        private var instance: AppDataBase? = null

        @Synchronized
        fun getDatabase(): AppDataBase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(App.getAppContext(),AppDataBase::class.java,"app_database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                //.allowMainThreadQueries()   //允许在主线程操作，主要在测试环境中
                .build()
                .apply {
                    instance = this
                }
        }
    }

}