package com.zmj.viewmodelpractice.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zmj.viewmodelpractice.R
import com.zmj.viewmodelpractice.databinding.ActL2RoomDbBinding
import com.zmj.viewmodelpractice.db.AppDataBase
import com.zmj.viewmodelpractice.db.BookDao
import com.zmj.viewmodelpractice.db.UserDao
import com.zmj.viewmodelpractice.entry.Book
import com.zmj.viewmodelpractice.entry.User
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
class L2RoomDbAct : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActL2RoomDbBinding = DataBindingUtil.setContentView(this,R.layout.act_l2_room_db)

        val userDao: UserDao = AppDataBase.getDatabase().userDao()
        val bookDao: BookDao = AppDataBase.getDatabase().bookDao()

        val user1 = User("Tom","Brady",40)
        val user2 = User("Tom","Hanks",60)

        binding.setAddData {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }

        binding.setUpdate {
            thread {
                user1.age = 42
                userDao.updateUser(user1)
            }
        }

        binding.setDelete {
            thread {
                userDao.deleteUserByLastName("Hanks")
            }
        }

        binding.setQueryData {
            thread {
                for (user in userDao.loadAllUsers()){
                    Log.d("L2RoomDbAct",user.toString())
                }
            }
        }

        binding.setBook {
            thread {
                bookDao.insert(Book("zmj${Random.nextInt(1000)}",Random.nextInt(1000),"first Code ${Random.nextInt(1000)}"))
            }
        }

        binding.setQueryBook {
            thread {
                for (book in bookDao.loadAllBooks()){
                    Log.d("L2RoomDbAct",book.toString())
                }
            }
        }
    }

}