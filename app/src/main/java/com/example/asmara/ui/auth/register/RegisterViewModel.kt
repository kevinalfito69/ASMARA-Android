package com.example.asmara.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.RegisterResponse
import com.example.asmara.api.response.RegisterUser
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun registerUser(user: RegisterUser) {
        val apiService = ApiConfig.getApiService()
        val call = apiService.registerUser(user)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerResult.value = response.body()
                } else {
                    _error.value = response.errorBody()?.string() ?: "Unknown error occurred"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _error.value = t.message
            }
        })
    }
}