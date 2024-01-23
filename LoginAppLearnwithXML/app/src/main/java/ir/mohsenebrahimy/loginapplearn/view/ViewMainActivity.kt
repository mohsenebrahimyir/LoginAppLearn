package ir.mohsenebrahimy.loginapplearn.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import ir.mohsenebrahimy.loginapplearn.databinding.ActivityMainBinding

class ViewMainActivity(contextInstance: Context) : FrameLayout(contextInstance) {

    val binding = ActivityMainBinding.inflate(
        LayoutInflater.from(context)
    )

    fun onClickHandler() {
        binding.btnSend.setOnClickListener {
            val email = binding.edtInputEmail.text.toString()
            val (isValid, error) = emailValidator(email)

            if (!isValid) {
                binding.textInputEmail.error = error
                return@setOnClickListener
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