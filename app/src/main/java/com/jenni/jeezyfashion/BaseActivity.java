package com.jenni.jeezyfashion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.jenni.jeezyfashion.models.User;
import com.jenni.jeezyfashion.network.auth.AuthResource;
import com.jenni.jeezyfashion.ui.login.LoginActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    public void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING: {
                            break;
                        }
                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: LOGIN SUCCESSFUL " + userAuthResource.data.getEmail());
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            navigateLoginScreen();
                            break;
                        }
                        case ERROR: {
                            Log.d(TAG, "onChanged: " + userAuthResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void navigateLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
