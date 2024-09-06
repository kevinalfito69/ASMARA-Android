package com.example.asmara.ui.homepage.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.WargaResponse
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()
    private val _wargaResult = MutableLiveData<WargaResponse>()

    private val _isTokenValid = MutableLiveData<Boolean>()
    val isTokenValid: LiveData<Boolean> get() = _isTokenValid

    val wargaResult: LiveData<WargaResponse> = _wargaResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchWarga(nik: String) {
        _isLoading.value = true  // Set loading indicator saat mulai
        apiService.getWargaByNik(nik).enqueue(object : Callback<WargaResponse> {
            override fun onResponse(call: Call<WargaResponse>, response: Response<WargaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val wargaBody = response.body()
                    Log.d("HomeViewModel", "Warga received: $wargaBody")
                    _wargaResult.value = wargaBody!!
                } else {
                    Log.e("HomeViewModel", "API Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WargaResponse>, t: Throwable) {
                _isLoading.value = false  // Matikan loading indicator jika terjadi kegagalan
                // Kamu bisa menambahkan log atau notifikasi ke pengguna di sini
            }
        })
    }


    fun verifyToken(context: Context) {
        val token = PreferencesUtils.getToken(context)
        if (token != null) {
            apiService.verifyToken("Bearer $token").enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    _isTokenValid.value = response.isSuccessful
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _isTokenValid.value = false
                }
            })
        } else {
            _isTokenValid.value = false
        }
    }

}