package com.jenni.jeezyfashion.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.jenni.jeezyfashion.BaseActivity
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.models.User
import com.jenni.jeezyfashion.network.auth.AuthResource
import com.jenni.jeezyfashion.ui.dashboard.DashboardViewModel
import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.nav_header.*
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var viewModel: DashboardViewModel

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, providerFactory).get(DashboardViewModel::class.java)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        val header = navigationView.getHeaderView(0)
        tvName = header.findViewById(R.id.tv_name)
        tvEmail = header.findViewById(R.id.tv_user_email)
        tvPhone = header.findViewById(R.id.tv_user_phone)

        // Get NavHostFragment and NavController
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController = navHostFrag.navController

        //Define AppBarConfiguration: Connect DrawerLayout with Navigation Component
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //Connect Toolbar with NavController
        toolbar.setupWithNavController(navController, appBarConfiguration)

        //Connect NavView with NavController
        bottomNavigationView.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)

        subscribeUser()
        Log.d("MActivity", "onCreate: MActivity")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isOpen) {
            drawerLayout.close()
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.logout()
                return true
            }
        }
        return true
    }

    private fun subscribeUser() {
        val userObserver = Observer<AuthResource<User>> { authUser ->
            if (authUser != null) {
                when (authUser.status) {
                    AuthResource.AuthStatus.AUTHENTICATED -> setUserDetails(authUser.data)
                    AuthResource.AuthStatus.ERROR -> setErrorDetails(authUser.message)
                }
            }
        }

        viewModel.authenticatedUser.observe(this, userObserver)
    }

    private fun setErrorDetails(message: String?) {
        tvName.text = message
        tvEmail.text = getString(R.string.error)
        tvPhone.text = getString(R.string.error)
    }

    private fun setUserDetails(data: User?) {
        tvName.text = data?.name
        tvEmail.text = data?.email
        tvPhone.text = data?.phone
    }
}