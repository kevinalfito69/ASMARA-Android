package com.example.asmara.ui.auth.addwarga


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.AddWargaResponse
import com.example.asmara.api.response.WargaResponse
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddWargaViewModel : ViewModel() {

    private val _addWargaResult = MutableLiveData<AddWargaResponse>()
    val addWargaResult: LiveData<AddWargaResponse> = _addWargaResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun addWarga(addWargaResponse: AddWargaResponse) {
        val apiService = ApiConfig.getApiService()
        val call = apiService.createWarga(addWargaResponse)

        call.enqueue(object : Callback<AddWargaResponse> {
            override fun onResponse(call: Call<AddWargaResponse>, response: Response<AddWargaResponse>) {
                if (response.isSuccessful) {
                    _addWargaResult.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error Body: $errorBody")
                    _error.value = errorBody ?: "Unknown error occurred"
                }
            }

            override fun onFailure(call: Call<AddWargaResponse>, t: Throwable) {
                _error.value = t.message
            }
        })
    }
}
