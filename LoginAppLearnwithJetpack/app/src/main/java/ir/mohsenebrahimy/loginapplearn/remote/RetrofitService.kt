package ir.mohsenebrahimy.loginapplearn.remote

import ir.mohsenebrahimy.loginapplearn.remote.apiRepository.LoginApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://learn.alirezaahmadi.info/api/v1/auth/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService : LoginApiService = retrofit.create(LoginApiService::class.java)
}