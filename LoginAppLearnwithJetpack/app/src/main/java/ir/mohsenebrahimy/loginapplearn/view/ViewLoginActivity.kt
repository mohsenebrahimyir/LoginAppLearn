package ir.mohsenebrahimy.loginapplearn.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ir.mohsenebrahimy.loginapplearn.androidWrapper.ActUtils
import ir.mohsenebrahimy.loginapplearn.remote.RetrofitService
import ir.mohsenebrahimy.loginapplearn.remote.dataModel.DefaultModel
import ir.mohsenebrahimy.loginapplearn.remote.dataModel.GetApiModel
import ir.mohsenebrahimy.loginapplearn.remote.ext.ErrorUtils
import ir.mohsenebrahimy.loginapplearn.ui.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewLoginActivity : FrameLayout {

    constructor(contextInstance: Context) : super(contextInstance)

    private lateinit var utils: ActUtils

    constructor(contextInstance: Context, utility: ActUtils) : super(contextInstance) {
        utils = utility
    }


    private val emailInput = mutableStateOf(TextFieldValue())
    private val codeInput = mutableStateOf(TextFieldValue())
    private val emailError = mutableStateOf(false)
    private val codeError = mutableStateOf(false)
    private val sendCodeVisibility = mutableStateOf(true)

    @Composable
    fun OnClickHandler(androidId: String) {

        var visibility by remember { sendCodeVisibility }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            if (visibility) {
                InputText("Email", emailInput, emailError, EMAIL_REGEX)
                RequestButton("Send Code") {
                    emailError.value = inputValidator(emailInput.value.text, EMAIL_REGEX)
                    if (!emailError.value) {
                        visibility = false

                        sendCodeInEmail(emailInput.value.text)
                    }
                }
            } else {
                Text(text = "Send code to ${emailInput.value.text}.")
                InputText("Code", codeInput, codeError, CODE_REGEX)
                RequestButton("Confirm") {
                    codeError.value = inputValidator(codeInput.value.text, CODE_REGEX)
                    if (!codeError.value) {
                        verifyCode(androidId, codeInput.value.text, emailInput.value.text)
                    }
                }
                TextButton(onClick = { visibility = true }) {
                    Text(text = "Is the ${emailInput.value.text} incorrect?")
                }
            }
        }
    }

    @Composable
    private fun InputText(
        label: String,
        valueInput: MutableState<TextFieldValue>,
        valueError: MutableState<Boolean>,
        regexError: String = ".*"
    ) {
        var input by remember { valueInput }
        var error by remember { valueError }

        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                error = inputValidator(input.text, regexError)
            },
            label = {
                Text(text = label)
            },
            isError = error,
            supportingText = {
                if (error) {
                    Text(text = "The ${input.text} is invalid")
                }
            }
        )
    }

    @Composable
    fun RequestButton(
        text: String,
        onClick: () -> Unit = {}
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(280.dp)
                .padding(top = 20.dp)
        ) {
            Text(text = text)
        }
    }

    private fun inputValidator(input: String, regex: String): Boolean {
        return (input.isEmpty() or !input.matches(regex.toRegex()))
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

    companion object {
        private const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        private const val CODE_REGEX = "^[0-9]{6}$"
    }
}