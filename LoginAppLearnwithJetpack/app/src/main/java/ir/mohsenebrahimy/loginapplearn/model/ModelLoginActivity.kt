package ir.mohsenebrahimy.loginapplearn.model

import android.content.Context
import ir.mohsenebrahimy.loginapplearn.androidWrapper.DeviceInfo

class ModelLoginActivity(private val context: Context) {
    fun getId() = DeviceInfo.getAndroidId(context)

}