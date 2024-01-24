package ir.mohsenebrahimy.loginapplearn.remote.apiRepository

import ir.mohsenebrahimy.loginapplearn.remote.dataModel.DefaultModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("email/login")
    suspend fun sendCodeInEmail(
        @Field("email") email: String
    ) : Response<DefaultModel>

    @FormUrlEncoded
    @POST("email/login/verify")
    suspend fun verifyCode(
        @Header("app-device-uid") androidId: String,
        @Field("code") code: String,
        @Field("email") email: String,
    ) : Response<DefaultModel>

}