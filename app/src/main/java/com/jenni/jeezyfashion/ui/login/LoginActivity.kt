package com.jenni.jeezyfashion.ui.login

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.models.User
import com.jenni.jeezyfashion.network.auth.AuthResource
import com.jenni.jeezyfashion.ui.main.MainActivity
import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), View.OnClickListener {
    private val TAG = "LoginActivity"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var drawable: Drawable

    @Inject
    lateinit var requestManager: RequestManager
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener(this)

        viewModel = ViewModelProvider(this, providerFactory).get(LoginViewModel::class.java)
        setLogo()
        subscribeObservers()
    }

    private fun setLogo() {
        requestManager
            .load(drawable)
            .into(iv_logo)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> attemptLogin()

        }
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(tv_user_id.text)) {
            return
        }
        viewModel.authenticateWithId((tv_user_id.text.toString()).toInt())
    }

    private fun subscribeObservers() {
        val userObserver = Observer<AuthResource<User>> { authUser ->
            if (authUser != null) {
                when (authUser.status) {
                    AuthResource.AuthStatus.LOADING -> showProgressBar(true)
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        showProgressBar(false)
                        onLoginSuccess()
                        Log.d(TAG, "subscribeObservers: LOGIN SUCCESSFUL " + authUser.data?.email)
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(this, authUser.message, Toast.LENGTH_SHORT).show()
                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> showProgressBar(false)

                }
            }
        }
        viewModel.observeAuthState().observe(this, userObserver)
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    private fun onLoginSuccess(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}