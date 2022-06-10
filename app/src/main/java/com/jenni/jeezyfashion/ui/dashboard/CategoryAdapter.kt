package com.jenni.jeezyfashion.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jenni.jeezyfashion.R
import com.jenni.jeezyfashion.util.CategoryModel

class CategoryAdapter(private val context: Context, var categoryList: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.setData(category, position)
    }

    override fun getItemCount(): Int = categoryList.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategoryName = itemView.findViewById<TextView>(R.id.tv_category_name)

        private var currentPosition: Int = -1
        private var categoryModel: CategoryModel? = null

        fun setData(categoryModel: CategoryModel, position: Int) {
            this.currentPosition = position
            this.categoryModel = categoryModel

            tvCategoryName.text = categoryModel.categoryName
        }
    }
}