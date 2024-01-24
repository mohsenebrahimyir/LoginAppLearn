package ir.mohsenebrahimy.loginapplearn.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import ir.mohsenebrahimy.loginapplearn.androidWrapper.ActUtils
import ir.mohsenebrahimy.loginapplearn.databinding.ActivityMainBinding
import ir.mohsenebrahimy.loginapplearn.remote.RetrofitService
import ir.mohsenebrahimy.loginapplearn.remote.dataModel.DefaultModel
import ir.mohsenebrahimy.loginapplearn.remote.dataModel.GetApiModel
import ir.mohsenebrahimy.loginapplearn.remote.ext.ErrorUtils
import ir.mohsenebrahimy.loginapplearn.ui.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewMainActivity : FrameLayout {

    constructor(contextInstance: Context) : super(contextInstance)

    private lateinit var utils: ActUtils

    constructor(contextInstance: Context, utility: ActUtils) : super(contextInstance) {
        utils = utility
    }

    val binding = ActivityMainBinding.inflate(
        LayoutInflater.from(context)
    )

    @SuppressLint("SetTextI18n")
    fun onClickHandler(androidId: String) {

        binding.btnSend.setOnClickListener {

            val email = binding.edtInputEmail.text.toString()
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
            val (isValid, error) = inputValidator(email, emailRegex)

            if (!isValid) {
                binding.textInputEmail.error = error
                return@setOnClickListener
            } else {
                binding.textInputEmail.error = null
            }

            sendCodeInEmail(email)

            binding.btnSend.visibility = INVISIBLE
            binding.textInputEmail.visibility = INVISIBLE

            binding.txtSendEmail.visibility = VISIBLE
            binding.textInputCode.visibility = VISIBLE
            binding.btnConfirm.visibility = VISIBLE
            binding.txtWrong.visibility = VISIBLE

            binding.txtSendEmail.text = "Send to email: $email"
        }

        binding.txtWrong.setOnClickListener {

            binding.btnSend.visibility = VISIBLE
            binding.textInputEmail.visibility = VISIBLE

            binding.txtSendEmail.visibility = INVISIBLE
            binding.textInputCode.visibility = INVISIBLE
            binding.btnConfirm.visibility = INVISIBLE
            binding.txtWrong.visibility = INVISIBLE
        }

        binding.btnConfirm.setOnClickListener {
            val email = binding.edtInputEmail.text.toString()
            val code = binding.edtCode.text.toString()
            val regex = "^[0-9]{6}$"
            val (isValid, error) = inputValidator(code, regex)

            if (!isValid) {
                binding.textInputCode.error = error
                return@setOnClickListener
            } else {
                binding.textInputCode.error = null
            }
            
            verifyCode(androidId, code, email)

        }
    }

    private fun inputValidator(input: String, regex: String): Pair<Boolean, String> {
        if (input.isEmpty()) {
            return false to "input is empty"
        } else if (!input.matches(regex.toRegex())) {
            return false to "The $input is invalid"
        }
        return true to "The $input is valid"
    }


    private fun sendCodeInEmail(email: String) {
        val service = RetrofitService.apiService
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.sendCodeInEmail(email)
                launch(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val data = response.body() as DefaultModel
                        Toast.makeText(
                            context,
                            data.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            ErrorUtils.getError(response),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.i("SERVER_ERROR", e.message.toString())
            }
        }
    }

    private fun verifyCode(androidId: String, code: String, email: String) {
        val service = RetrofitService.apiService
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.verifyCode(androidId, code, email)
                launch(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val data = response.body() as GetApiModel

                        /***
                         TODO:
                         save data.api in Room Database and use it for send request with
                         authentication mode and do something ...
                        ***/

                        Toast.makeText(
                            context,
                            data.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        context.startActivity(Intent(context, HomeActivity::class.java))
                        utils.finished()
                    } else {
                        Toast.makeText(
                            context,
                            ErrorUtils.getError(response),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.i("SERVER_ERROR", e.message.toString())
            }
        }

    }
}