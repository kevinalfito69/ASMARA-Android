import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.UpdateProfileRequest
import com.example.asmara.api.response.UpdateProfileResponse
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileViewModel : ViewModel() {
    private val _updateProfileResult = MutableLiveData<UpdateProfileResponse>()
    val updateProfileResult: LiveData<UpdateProfileResponse> = _updateProfileResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val apiService = ApiConfig.getApiService()

    fun updateProfile(token: String, username: String, email: String) {
        _isLoading.value = true

        val updateProfileRequest = UpdateProfileRequest(username, email)

        apiService.updateProfile(token, updateProfileRequest).enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(call: Call<UpdateProfileResponse>, response: Response<UpdateProfileResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _updateProfileResult.value = response.body()
                } else {
                    _error.value = "Update failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}
