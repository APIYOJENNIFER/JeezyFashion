package com.jenni.jeezyfashion.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.ui.main.MainActivity
import com.jenni.jeezyfashion.util.PreferenceManager
import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_otp.*
import javax.inject.Inject

class OTPActivity : DaggerAppCompatActivity(), View.OnClickListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storedVerificationId: String
    private lateinit var viewModel: LoginViewModel
    private lateinit var preferenceManager: PreferenceManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        preferenceManager = PreferenceManager(this)
        btn_login.setOnClickListener(this)
        tv_resend_code.setOnClickListener(this)

        viewModel = ViewModelProvider(this, providerFactory).get(LoginViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()

        storedVerificationId = intent.getStringExtra("storedVerificationId").toString()

        Log.d("OTPActivity", "onCreate: OTPActivity")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> login()
            R.id.tv_resend_code -> resendCode()
        }
    }

    private fun resendCode(){
        preferenceManager.waitingForCode = false
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        val code = et_code.text.trim().toString()
        if (TextUtils.isEmpty(et_code.text)) {
            et_code.error = "Enter Code"
        } else {
            val credential: PhoneAuthCredential =
                PhoneAuthProvider.getCredential(storedVerificationId, code)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user

                    preferenceManager.loggedIn = true
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid Code", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}