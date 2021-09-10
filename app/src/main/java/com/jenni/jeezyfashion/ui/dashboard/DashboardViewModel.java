package com.jenni.jeezyfashion.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jenni.jeezyfashion.SessionManager;
import com.jenni.jeezyfashion.models.User;
import com.jenni.jeezyfashion.network.auth.AuthResource;

import javax.inject.Inject;

public class DashboardViewModel extends ViewModel {
    private static final String TAG = "DashboardViewModel";
    private final SessionManager sessionManager;

    @Inject
    public DashboardViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "DashboardViewModel: viewModel is ready...");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }
}
