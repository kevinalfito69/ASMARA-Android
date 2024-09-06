package com.example.asmara.ui.homepage.profile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.ChangePasswordRequest
import com.example.asmara.api.response.ChangePasswordResponse
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {

    private val apiService = ApiConfig.getApiService()

    private val _changePasswordResult = MutableLiveData<ChangePasswordResponse>()
    val changePasswordResult: LiveData<ChangePasswordResponse> = _changePasswordResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun changePassword(token: String, oldPassword: String, newPassword: String) {
        _isLoading.value = true
        val request = ChangePasswordRequest(oldPassword, newPassword)
        apiService.changePassword(token, request).enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _changePasswordResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}