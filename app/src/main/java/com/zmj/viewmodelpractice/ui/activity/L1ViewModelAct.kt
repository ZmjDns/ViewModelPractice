package com.zmj.viewmodelpractice.ui.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.zmj.viewmodelpractice.R
import com.zmj.viewmodelpractice.databinding.ActivityMainBinding
import com.zmj.viewmodelpractice.ui.MyObserver
import com.zmj.viewmodelpractice.viewmodel.MainViewModel
import com.zmj.viewmodelpractice.viewmodel.MainViewModelFactory
import kotlin.random.Random

class L1ViewModelAct : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(MyObserver(lifecycle))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved",0)

        //mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel = ViewModelProviders.of(this,
            MainViewModelFactory(
                countReserved
            )
        ).get(MainViewModel::class.java)


        //--------------------------------计数相关-----------------------------
        binding.setOnMyClick{
            mainViewModel.plusOne()
        }
        binding.setOnClearClick {
            mainViewModel.clear()
        }
        /*mainViewModel.counter.observe(this, Observer { count ->
            binding.num = count
        })*/
        //等价于上面方法
        mainViewModel.counter.observe(this){ count ->
            binding.num = count
        }
        //--------------------------------计数相关-----------------------------


        //----------------------------user相关-----------------------------------
        binding.setGetUser {
            mainViewModel.getUser(Random.nextInt(1000).toString())
            Log.e("MainActivity","clicksetGetUser......")
        }

        mainViewModel.user.observe(this, Observer { user ->
            binding.userInfo = user.firstName
        })
        //----------------------------user相关-----------------------------------



    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved",mainViewModel.counter.value ?: 0)
        }
    }
}