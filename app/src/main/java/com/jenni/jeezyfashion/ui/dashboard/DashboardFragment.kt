package com.jenni.jeezyfashion.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.models.User
import com.jenni.jeezyfashion.network.auth.AuthResource
import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class DashboardFragment : DaggerFragment() {
    private val TAG = "DashboardFragment"
    private lateinit var viewModel: DashboardViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: dashboard fragment created...")
        viewModel = ViewModelProvider(this, providerFactory).get(DashboardViewModel::class.java)
        subscribeObservers()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        cv_dresses.setOnClickListener { v ->
            val action = DashboardFragmentDirections.dashboardToDresses()
            v.findNavController().navigate(action)
        }
    }

    private fun subscribeObservers() {
        val userObserver = Observer<AuthResource<User>> { authUser ->
            if (authUser != null) {
                when (authUser.status) {
                    AuthResource.AuthStatus.AUTHENTICATED -> setUserDetails(authUser.data)
                    AuthResource.AuthStatus.ERROR -> setErrorDetails(authUser.message)
                }

            }
        }
        viewModel.authenticatedUser.removeObservers(viewLifecycleOwner)
        viewModel.authenticatedUser.observe(viewLifecycleOwner, userObserver)
    }

    private fun setErrorDetails(message: String?) {

    }

    private fun setUserDetails(data: User?) {

    }
}