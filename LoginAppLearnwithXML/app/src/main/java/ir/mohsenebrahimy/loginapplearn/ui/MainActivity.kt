package ir.mohsenebrahimy.loginapplearn.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.mohsenebrahimy.loginapplearn.model.ModelMainActivity
import ir.mohsenebrahimy.loginapplearn.presenter.PresenterMainActivity
import ir.mohsenebrahimy.loginapplearn.view.ViewMainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewMainActivity(this)
        val model = ModelMainActivity(this)
        setContentView(view.binding.root)

        val presenter = PresenterMainActivity(view, model)
        presenter.onCreatePresenter()
    }
}