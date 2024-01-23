package ir.mohsenebrahimy.loginapplearn.presenter

import ir.mohsenebrahimy.loginapplearn.model.ModelMainActivity
import ir.mohsenebrahimy.loginapplearn.view.ViewMainActivity

class PresenterMainActivity(
    private val view: ViewMainActivity,
    private val model: ModelMainActivity
) {

    fun onCreatePresenter() {
        view.onClickHandler()
    }
}