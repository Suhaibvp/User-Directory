package com.kenzo.userdictionary.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenzo.userdictionary.ui.model.User
import com.kenzo.userdictionary.ui.retrofit.ApiClient
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val _users= MutableLiveData<List<User>>()
    val users:LiveData<List<User>>get() = _users

    private val _errors=MutableLiveData<String?>()
    val errors: LiveData<String?>get()=_errors

    fun fetchUser(){
        viewModelScope.launch {
            try {
                val result= ApiClient.instance.getUsers()
                if(result.isSuccessful) {
                    Log.d("ApiCall", "Api call was succesfull")
                    _users.value = result.body()
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

