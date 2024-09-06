package com.example.asmara.api.retrofit

import com.example.asmara.api.response.AddWargaResponse
import com.example.asmara.api.response.AspirasiPost
import com.example.asmara.api.response.ChangePasswordRequest
import com.example.asmara.api.response.ChangePasswordResponse

import com.example.asmara.api.response.LoginPost
import com.example.asmara.api.response.LoginResponse
import com.example.asmara.api.response.Pembangunan
import com.example.asmara.api.response.RegisterResponse
import com.example.asmara.api.response.RegisterUser
import com.example.asmara.api.response.UpdateProfileRequest
import com.example.asmara.api.response.UpdateProfileResponse
import com.example.asmara.api.response.UpdateWargaResponse
import com.example.asmara.api.response.UserProfileResponse
import com.example.asmara.api.response.WargaResponse
import com.example.asmara.api.response.Wilayah
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("verify-token/")
    fun verifyToken(@Header("Authorization") token: String): Call<Void>
    @GET("pembangunan/")
    fun getPembangunan(): Call<List<Pembangunan>>
    @GET("wilayah/")
    fun getWilayah(): Call<List<Wilayah>>
    @GET("warga/{nik}/")
    fun getWargaByNik(@Path("nik") nik: String): Call<WargaResponse>
    @POST("login/")
    fun login(@Body loginRequest: LoginPost): Call<LoginResponse>
    @POST("aspirasi/")
    fun postAspirasi(@Body aspirasi: AspirasiPost): Call<Void>
    @POST("register/")
    fun registerUser(@Body user: RegisterUser): Call<RegisterResponse>
    @POST("wargacreate/")
    fun createWarga(@Body addWargaResponse: AddWargaResponse): Call<AddWargaResponse>
    @PUT("warga/{nik}/edit/")
    fun updateWarga(
        @Path("nik") nik: String,
        @Body updateWargaRequest: UpdateWargaResponse
    ): Call<UpdateWargaResponse>
    @POST("change-password/")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Call<ChangePasswordResponse>
    @PUT("update-profile/")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Call<UpdateProfileResponse>
    @GET("profile/")
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<UserProfileResponse>
}
