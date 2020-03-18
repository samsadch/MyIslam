package com.samsad.myislam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samsad.myislam.R

class MainAdapter(var context:Context, private var list:ArrayList<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var inflater:LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_cards,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemTxv.text = item
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val itemTxv:TextView = itemView.findViewById(R.id.itemTxv)
        val itemImv:ImageView = itemView.findViewById(R.id.itemImv)
    }
}