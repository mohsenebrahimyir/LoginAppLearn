package ir.mohsenebrahimy.loginapplearn.presenter

import androidx.compose.runtime.Composable
import ir.mohsenebrahimy.loginapplearn.model.ModelLoginActivity
import ir.mohsenebrahimy.loginapplearn.view.ViewLoginActivity

class PresenterLoginActivity(
    private val view: ViewLoginActivity,
    private val model: ModelLoginActivity
) {

    @Composable
    fun OnCreatePresenter() {
        view.OnClickHandler(model.getId())
    }
}