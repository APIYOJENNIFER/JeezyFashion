package com.jenni.jeezyfashion

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {
    @Inject lateinit var drawable: Drawable

    @Inject lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setLogo()
    }

    private fun setLogo(){
        requestManager
            .load(drawable)
            .into(iv_logo)
    }
}