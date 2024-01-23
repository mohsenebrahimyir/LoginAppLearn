package ir.mohsenebrahimy.loginapplearn.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import ir.mohsenebrahimy.loginapplearn.databinding.ActivityMainBinding
import ir.mohsenebrahimy.loginapplearn.model.ErrorModel
import ir.mohsenebrahimy.loginapplearn.remote.RetrofitService
import ir.mohsenebrahimy.loginapplearn.remote.dataModel.DefaultModel
import ir.mohsenebrahimy.loginapplearn.remote.ext.ErrorUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewMainActivity(contextInstance: Context) : FrameLayout(contextInstance) {

    val binding = ActivityMainBinding.inflate(

        LayoutInflater.from(context)

    )

    @SuppressLint("SetTextI18n")
    fun onClickHandler() {

        binding.btnSend.setOnClickListener {

            val email = binding.edtInputEmail.text.toString()
            val (isValid, error) = emailValidator(email)

            if (!isValid) {
                binding.textInputEmail.error = error
                return@setOnClickListener
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
    }

    private fun sendCodeInEmail(email: String) {

        val service = RetrofitService.apiService
        CoroutineScope(Dispatchers.IO).launch {
            try {


                val response = service.sendRequest(email)
                if (response.isSuccessful) {
                    launch(Dispatchers.Main) {
                        val data = response.body() as DefaultModel
                        Toast.makeText(
                            context,
                            data.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    launch(Dispatchers.Main) {
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

    private fun emailValidator(email: String): Pair<Boolean, String> {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

        if (email.isEmpty()) {
            return false to "The email is empty"
        } else if (!email.matches(emailRegex.toRegex())) {
            return false to "The email is invalid"
        }

        return true to "The email is valid"
    }
}