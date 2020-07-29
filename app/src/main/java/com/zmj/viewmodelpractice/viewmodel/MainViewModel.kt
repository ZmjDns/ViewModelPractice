package com.zmj.viewmodelpractice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zmj.viewmodelpractice.entry.User
import com.zmj.viewmodelpractice.repository.Repository

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/29
 * Description :
 */
class MainViewModel(countReserved:Int): BaseViewModel() {

    //---------------------------------------------------
    val counter: LiveData<Int>
        get() = _counter

    private val _counter = MutableLiveData<Int>()
    init {
        _counter.value = countReserved
    }

    fun plusOne(){
        val count = counter.value ?: 0
        _counter.value = count + 1
    }
    fun clear(){
        _counter.value = 0
    }
    //---------------------------------------------------


    //-----------------------------------对象相关----------------------------------------------
    //map()用法,将对象中的部分属性转换成需要展示用户的数据类型。
    // 比如我们只需要展示用户的姓名，就用Transformations.map(user)转换就行了，而不必将整个对象传出去
    private val userLiveData = MutableLiveData<User>()
    val userName: LiveData<String> = Transformations.map(userLiveData){ user ->
        "${user.firstName} ${user.lastName}"
    }
    val userAge: LiveData<Int> = Transformations.map(userLiveData){ user ->
        user.age
    }
    //switchMap()用法，如果ViewModel中的某个LiveData对象是调用另外的方法获取的，
    //那么我们就需要借助switchMap()方法将这个LiveData对象转换成 !!!另外一个!!! 可观察的LiveData对象
    private val userIdLiveData = MutableLiveData<String>()
    val user: LiveData<User> = Transformations.switchMap(userIdLiveData) { userId ->
        Repository.getUser(userId)
    }
    /**
     * 这个方法看起来没有用，其实是调动数据更新的关键，具体的运作流程如下：
     * 1.Activity中调用getUser（userId）并传来userId,将userId赋值给 userIdLiveData，
     * 2.而userIdLiveData是一个可以观察的LiveData，一旦userIdLiveData数据改变，观察userIdLiveData的switchMap（）就会执行
     * 3.并且调用Repository.getUser(userId)来获取真正的用户数据，
     * 并将Repository.getUser(userId)返回来的LiveData转换成一个可观察的LiveData对象
     * 对于Activity而言只需要观察这个LiveData对象就可以了
     */
    fun getUser(userId: String){
        userIdLiveData.value = userId
    }
    //-----------------------------------对象相关----------------------------------------------


    //----------------------------再没有可观察数据的情况下----------------------------
    //我们需要创建一个空的LiveData对象
    private val refreshLiveData = MutableLiveData<Any>()
    val refreshResult: LiveData<String> = Transformations.switchMap(refreshLiveData){
        Repository.refresh()
    }
    fun refresh(){
        //触发数据变化
        //LiveData内部不会判断即将设置的数据和原有数据是否相同，只要掉用了setValue（）或postValue()就会触发数据变化事件
        refreshLiveData.value = refreshLiveData.value
    }
    //----------------------------再没有可观察数据的情况下----------------------------


}