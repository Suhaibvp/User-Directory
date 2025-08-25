package com.kenzo.userdictionary.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenzo.userdictionary.ui.model.Post
import com.kenzo.userdictionary.ui.model.User
import com.kenzo.userdictionary.ui.retrofit.ApiClient
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val _posts= MutableLiveData<List<Post>>()
    val posts:LiveData<List<Post>>get() = _posts

    private val _errors=MutableLiveData<String?>()
    val errors: LiveData<String?>get()=_errors

    fun fetchPosts(userId: Long){
        viewModelScope.launch {
            try {
                val result= ApiClient.instance.getPosts(userId)
                if(result.isSuccessful) {
                    Log.d("ApiCall", "Api call was succesfull")
                    _posts.value = result.body()
                    _errors.value=null
                }else{
                    _errors.value="faied to Fetch details ${result.code()}"
                }
            }
            catch (ex: Exception){
                _errors.value="Failed to fetch user details ${ex.message}"
            }


        }
    }
}