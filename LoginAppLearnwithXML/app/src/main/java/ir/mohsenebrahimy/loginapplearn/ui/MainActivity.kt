package ir.mohsenebrahimy.loginapplearn.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.mohsenebrahimy.loginapplearn.androidWrapper.ActUtils
import ir.mohsenebrahimy.loginapplearn.model.ModelMainActivity
import ir.mohsenebrahimy.loginapplearn.presenter.PresenterMainActivity
import ir.mohsenebrahimy.loginapplearn.view.ViewMainActivity

class MainActivity : AppCompatActivity(), ActUtils {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewMainActivity(this, this)
        val model = ModelMainActivity(this)
        setContentView(view.binding.root)

        val presenter = PresenterMainActivity(view, model)
        presenter.onCreatePresenter()

    }

    override fun finished() {
        finished()
    }
}