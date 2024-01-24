package ir.mohsenebrahimy.loginapplearn.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ir.mohsenebrahimy.loginapplearn.androidWrapper.ActUtils
import ir.mohsenebrahimy.loginapplearn.model.ModelLoginActivity
import ir.mohsenebrahimy.loginapplearn.presenter.PresenterLoginActivity
import ir.mohsenebrahimy.loginapplearn.view.ViewLoginActivity


class LoginActivity : ComponentActivity(), ActUtils {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val presenter = PresenterLoginActivity(
            ViewLoginActivity(this, this),
            ModelLoginActivity(this)
        )

        setContent { presenter.OnCreatePresenter() }
    }

    override fun finished() { finished() }
}
