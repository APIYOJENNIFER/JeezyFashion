package com.jenni.jeezyfashion.ui.dresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.util.DressesModel
import dagger.android.support.DaggerFragment

class DressesFragment : DaggerFragment() {
    private val dressesList = arrayListOf<DressesModel>()
    private lateinit var dressesAdapter: DressesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dresses, container, false)
        recyclerView = view.findViewById(R.id.rv_dresses)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDummyData()
        setupRecyclerview()
    }

    private fun setupDummyData() {
        val dresses = DressesModel()
        for (i in 1..5){
            dresses.dressPrice = "UGX 20,000"
            dresses.dressTitle = "Short party dress"
            dressesList.add(dresses)
        }
    }

    private fun setupRecyclerview() {
        dressesAdapter = DressesAdapter(requireContext(), dressesList)
        recyclerView.adapter = dressesAdapter
        recyclerView.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
    }
}