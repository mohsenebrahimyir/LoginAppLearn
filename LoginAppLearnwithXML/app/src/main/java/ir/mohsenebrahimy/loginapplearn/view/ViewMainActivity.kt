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