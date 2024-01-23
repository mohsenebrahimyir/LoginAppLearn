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
            if (isEmailInvalid()) return@setOnClickListener
        }
    }

    private fun isEmailInvalid(): Boolean {
        val email = binding.edtInputEmail.text.toString()
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

        if (email.isEmpty()) {
            binding.textInputEmail.error = "Email is Empty"
            return true
        } else if (!email.matches(emailRegex.toRegex())) {
            binding.textInputEmail.error = "Email is not valid format"
            return true
        }

        return false
    }
}