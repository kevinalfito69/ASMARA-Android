import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asmara.api.response.AspirasiPost
import com.example.asmara.api.response.Pembangunan
import com.example.asmara.api.response.Wilayah
import com.example.asmara.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AspirasiViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _pembangunanList = MutableLiveData<List<Pembangunan>>()
    val pembangunanList: LiveData<List<Pembangunan>> get() = _pembangunanList

    private val _wilayahList = MutableLiveData<List<Wilayah>>()
    val wilayahList: LiveData<List<Wilayah>> get() = _wilayahList

    private val _postResult = MutableLiveData<Boolean>()
    val postResult: LiveData<Boolean> get() = _postResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchPembangunan() {
        _isLoading.value = true
        apiService.getPembangunan().enqueue(object : Callback<List<Pembangunan>> {
            override fun onResponse(
                call: Call<List<Pembangunan>>,
                response: Response<List<Pembangunan>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _pembangunanList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Pembangunan>>, t: Throwable) {
                _isLoading.value = false
                _pembangunanList.value = emptyList()
            }
        })
    }

    fun fetchWilayah() {
        _isLoading.value = true
        apiService.getWilayah().enqueue(object : Callback<List<Wilayah>> {
            override fun onResponse(call: Call<List<Wilayah>>, response: Response<List<Wilayah>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _wilayahList.value = response.body()

                }
            }

            override fun onFailure(call: Call<List<Wilayah>>, t: Throwable) {
                _isLoading.value = false
                _wilayahList.value = emptyList()
            }
        })
    }

    fun postAspirasi(aspirasi: AspirasiPost) {
        _isLoading.value = true
        apiService.postAspirasi(aspirasi).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _postResult.value = response.isSuccessful
                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.value = false
                _postResult.value = false
            }
        })
    }
}
