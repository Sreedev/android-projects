package com.sample.slothyhacker.recyclerviewwithkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class NewsListAdapter(private val rainfallData: ArrayList<MainActivity.Rainfall>) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    //Initiate and inflate the main view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    //Binds each data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(rainfallData[position])
    }

    //Returns the size of news list
    override fun getItemCount(): Int {
        return rainfallData.size
    }

    //Holder class holding and initiating all the views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: MainActivity.Rainfall) {
            itemView.tv_state.text = user.newsTitle
            itemView.tv_unit.text = user.shortDesc
        }
    }

}