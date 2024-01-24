package ir.mohsenebrahimy.loginapplearn.remote.ext

import com.google.gson.Gson
import ir.mohsenebrahimy.loginapplearn.model.ErrorModel
import retrofit2.Response

object ErrorUtils {

    fun getError(response: Response<*>) : String {
        var error : String? = null
        val  errorBody = response.errorBody()

        if (errorBody != null)
            error = Gson().fromJson(errorBody.string(), ErrorModel::class.java).message

        return error ?: "Communication with the server is not possible!"
    }
}