package com.jenni.jeezyfashion.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.models.User
import com.jenni.jeezyfashion.network.auth.AuthResource
import com.jenni.jeezyfashion.util.CategoryModel
import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class DashboardFragment : DaggerFragment() {
    private val TAG = "DashboardFragment"
    private lateinit var viewModel: DashboardViewModel
    private val categoryList = arrayListOf<CategoryModel>()
    private lateinit var categoryAdapter:CategoryAdapter
    private lateinit var recyclerview:RecyclerView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerview = view.findViewById(R.id.rv_category)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: dashboard fragment created...")
        viewModel = ViewModelProvider(this, providerFactory).get(DashboardViewModel::class.java)
        subscribeObservers()
//        initOnClickListeners()

        setDummyData()
        setUpRecyclerview()
    }

//    private fun initOnClickListeners() {
//        cv_dresses.setOnClickListener { v ->
//            val action = DashboardFragmentDirections.dashboardToDresses()
//            v.findNavController().navigate(action)
//        }
//    }

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

     private fun setDummyData(){
         val category = CategoryModel()
         for (i in 1..6){
             category.categoryName = "Bags"

             categoryList.add(category)
         }
     }

    private fun setUpRecyclerview(){
        categoryAdapter = CategoryAdapter(requireContext(), categoryList)
        recyclerview.adapter = categoryAdapter
        recyclerview.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        recyclerview.layoutManager = gridLayoutManager
    }
}