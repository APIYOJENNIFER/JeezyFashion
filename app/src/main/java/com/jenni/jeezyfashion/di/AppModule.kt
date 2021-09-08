package com.jenni.jeezyfashion.di

import android.app.Application
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.RequestOptions
import com.jenni.jeezyfashion.R
import com.bumptech.glide.RequestManager
import com.bumptech.glide.Glide
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.placeholder)
            .error(R.drawable.placeholder)
    }

    @Provides
    fun provideGlideInstance(
        application: Application?,
        requestOptions: RequestOptions?
    ): RequestManager {
        return Glide.with(application!!)
            .setDefaultRequestOptions(requestOptions!!)
    }

    @Provides
    fun provideAppDrawable(application: Application?): Drawable {
        return ContextCompat.getDrawable(application!!, R.drawable.logo)!!
    }
}