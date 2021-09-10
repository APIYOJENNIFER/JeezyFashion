package com.jenni.jeezyfashion.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.jenni.jeezyfashion.SessionManager;
import com.jenni.jeezyfashion.models.User;
import com.jenni.jeezyfashion.network.auth.AuthApi;
import com.jenni.jeezyfashion.network.auth.AuthResource;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";
    private final AuthApi authApi;
    private SessionManager sessionManager;

    @Inject
    public LoginViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "LoginViewModel: is working...");
    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: attempting to login...");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId){
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @NonNull
                            @Override
                            public User apply(@NonNull Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {

                            @NonNull
                            @Override
                            public AuthResource<User> apply(@NonNull User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResource.error("Could not authenticate", null);

                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
