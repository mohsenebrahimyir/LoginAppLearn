package ir.mohsenebrahimy.loginapplearn.remote.apiRepository

import ir.mohsenebrahimy.loginapplearn.remote.dataModel.DefaultModel
import ir.mohsenebrahimy.loginapplearn.remote.dataModel.GetApiModel
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
    ) : Response<GetApiModel>

    /***
    TODO: send request with authentication with api-key and android id ...

    @FormUrlEncoded
    @POST("product/add")
    suspend fun addProduct(
    @Header("app-device-uid") androidId: String,
    @Header("app-api-key") api: String,
    @Field("id") id: String,
    ) : Response<GetApiModel>
     ***/

}