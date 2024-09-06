package com.example.asmara.ui.homepage.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.UserProfileResponse
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<UserProfileResponse>()
    val userProfile: LiveData<UserProfileResponse> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val apiService = ApiConfig.getApiService()

    fun fetchUserProfile(token: String) {
        _isLoading.value = true

        apiService.getUserProfile(token).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userProfile.value = response.body()
                } else {
                    _error.value = "Failed to fetch profile: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
