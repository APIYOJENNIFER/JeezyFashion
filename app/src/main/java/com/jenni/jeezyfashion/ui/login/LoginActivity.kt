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
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.models.User
import com.jenni.jeezyfashion.network.auth.AuthResource
import com.jenni.jeezyfashion.ui.main.MainActivity
import com.jenni.jeezyfashion.util.Constants.db
import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), View.OnClickListener {
    private val TAG = "LoginActivity"
    private lateinit var phoneNumber: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

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
        btn_submit.setOnClickListener(this)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.useAppLanguage()

        viewModel = ViewModelProvider(this, providerFactory).get(LoginViewModel::class.java)
//        subscribeObservers()


        authenticateUser()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_submit -> {
                attemptLogin()
            }

        }
    }

    private fun attemptLogin() {
        phoneNumber = country_code_picker.selectedCountryCodeWithPlus + tv_user_phone.text

        if (TextUtils.isEmpty(tv_user_firstname.text)) {
            tv_user_firstname.error = "Enter First Name"
            return
        } else if (TextUtils.isEmpty(tv_user_surname.text)) {
            tv_user_surname.error = "Enter Surname"
            return
        } else if (TextUtils.isEmpty(tv_user_phone.text)) {
            tv_user_phone.error = "Enter Phone"
            return
        } else {
            sendVerificationCode()
            showProgressBar(true)
        }

        val user = hashMapOf(
            "user_firstname" to tv_user_firstname.text,
            "user_surname" to tv_user_surname.text,
            "user_phone" to tv_user_phone.text
        )

//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//        viewModel.authenticateWithId((tv_user_id.text.toString()).toInt())

//        authenticateUser()
    }

    private fun sendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun authenticateUser() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                showProgressBar(false)
                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                showProgressBar(false)

                val intent = Intent(applicationContext, OTPActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
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

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}