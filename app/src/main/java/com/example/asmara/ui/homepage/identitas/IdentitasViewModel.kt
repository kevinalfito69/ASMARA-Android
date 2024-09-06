package com.example.asmara.ui.homepage.identitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.UpdateWargaResponse
import com.example.asmara.api.response.WargaResponse
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IdentitasViewModel: ViewModel() {
    private val apiService = ApiConfig.getApiService()
    private val _wargaResult = MutableLiveData<WargaResponse>()
    val wargaResult: LiveData<WargaResponse> = _wargaResult

    private val _updateResult = MutableLiveData<UpdateWargaResponse>()
    val updateResult: LiveData<UpdateWargaResponse> = _updateResult
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchWarga(nik:String) {
        _isLoading.value = true
        apiService.getWargaByNik(nik).enqueue(object : Callback<WargaResponse> {
            override fun onResponse(call: Call<WargaResponse>, response: Response<WargaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _wargaResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<WargaResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
    fun updateWarga(nik: String, updateWargaRequest: UpdateWargaResponse) {
        _isLoading.value = true
        apiService.updateWarga(nik, updateWargaRequest).enqueue(object : Callback<UpdateWargaResponse> {
            override fun onResponse(call: Call<UpdateWargaResponse>, response: Response<UpdateWargaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _updateResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<UpdateWargaResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}