package com.jenni.jeezyfashion.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

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
    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    public LoginViewModel(AuthApi authApi) {
        this.authApi = authApi;
        Log.d(TAG, "LoginViewModel: is working...");
    }

    public void authenticateWithId(int userId){
        authUser.setValue(AuthResource.loading(null));
        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
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
                                if (user.getId()==-1){
                                    return AuthResource.error("Could not authenticate", null);

                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                .subscribeOn(Schedulers.io())
        );

        authUser.addSource(source, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }

    public LiveData<AuthResource<User>> observeUser(){
        return authUser;
    }
}
