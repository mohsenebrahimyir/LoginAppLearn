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
            if (binding.edtInputEmail.text.toString().isEmpty()) {
                binding.textInputEmail.error = "Email Empty"
                return@setOnClickListener
            }
        }
    }
}