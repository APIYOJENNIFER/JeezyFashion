package com.jenni.jeezyfashion.ui.dresses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.util.DressesModel

class DressesAdapter(private val context: Context, var dressesList: ArrayList<DressesModel>) :
    RecyclerView.Adapter<DressesAdapter.DressesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DressesViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_dresses, parent, false)
        return DressesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DressesViewHolder, position: Int) {
        val dresses = dressesList[position]
        holder.setData(dresses, position)
    }

    override fun getItemCount(): Int = dressesList.size

    inner class DressesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDressesTitle = itemView.findViewById<TextView>(R.id.tv_dress_name)
        private val tvDressesPrice = itemView.findViewById<TextView>(R.id.tv_dress_price)

        private var currentPosition: Int = -1
        private var dressesModel: DressesModel? = null

        fun setData(dressesModel: DressesModel, position: Int) {
            this.currentPosition = position
            this.dressesModel = dressesModel

            tvDressesTitle.text = dressesModel.dressTitle
            tvDressesPrice.text = dressesModel.dressPrice
        }
    }
}