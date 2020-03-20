package com.samsad.myislam.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.samsad.myislam.R
import com.samsad.myislam.model.ModelRecords

class AdapterRecord() : RecyclerView.Adapter<AdapterRecord.HolderRecord>() {

    private var context: Context? = null
    private var recordList: ArrayList<ModelRecords>? = null

    constructor(context: Context, recordList: ArrayList<ModelRecords>?) : this() {
        this.context=context
        this.recordList=recordList
    }

    inner class HolderRecord(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileImv: ImageView = itemView.findViewById(R.id.profileImv)
        var nameTxv: TextView = itemView.findViewById(R.id.nameTxv)
        var descTxv: TextView = itemView.findViewById(R.id.descTxv)
        var dateTxv: TextView = itemView.findViewById(R.id.dateTxv)
        var moreButton: ImageButton = itemView.findViewById(R.id.moreButton)
        var mainCard: CardView = itemView.findViewById(R.id.mainCard)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecord {
        return HolderRecord(
            LayoutInflater.from(context).inflate(R.layout.list_item_records, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recordList!!.size
    }

    override fun onBindViewHolder(holder: HolderRecord, position: Int) {
        val item = recordList?.get(position)
        val image = item?.image
        val name = item?.name
        val desc = item?.desc
        val date = item?.addTime
        val id = item?.id

        holder.nameTxv.text=name
        holder.descTxv.text=desc
        holder.dateTxv.text=date
        if (image=="null"){
            holder.profileImv.setImageResource(R.drawable.ic_person)
        }else{
            holder.profileImv.setImageURI(Uri.parse(image))
        }

        holder.moreButton.setOnClickListener{

        }

    }
}