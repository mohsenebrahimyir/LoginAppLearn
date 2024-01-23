package ir.mohsenebrahimy.loginapplearn.remote.apiRepository

import ir.mohsenebrahimy.loginapplearn.remote.dataModel.DefaultModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("email/login")
    suspend fun sendRequest(
        @Field("email") email: String
    ) : Response<DefaultModel>

}