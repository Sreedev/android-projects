package com.sample.slothyhacker.recyclerviewwithkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class NewsListAdapter(private val newsDataList: ArrayList<MainActivity.NewsList>) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    //Initiate and inflate the main view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    //Binds each data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(newsDataList[position])
    }

    //Returns the size of news list
    override fun getItemCount(): Int {
        return newsDataList.size
    }

    //Holder class holding and initiating all the views
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: MainActivity.NewsList) {
            itemView.tv_heading.text = user.newsTitle
            itemView.tv_shortdesc.text = user.shortDesc
        }
    }

}