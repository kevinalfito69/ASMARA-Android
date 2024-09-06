package com.example.asmara.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmara.api.response.LoginPost
import com.example.asmara.api.response.LoginResponse
import com.example.asmara.api.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun loginUser(username: String, password: String) {
        val loginRequest = LoginPost(username, password)
        val call = apiService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _loginResult.value = Result.success(response.body()!!)
                } else {
                    _loginResult.value = Result.failure(Exception("Login failed: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = Result.failure(t)
            }
        })
    }
}
