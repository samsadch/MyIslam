package com.samsad.myislam.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.samsad.myislam.R
import com.samsad.myislam.activity.AddUpdateRecordActivity
import com.samsad.myislam.activity.RecordsDetailActivity
import com.samsad.myislam.model.ModelRecords
import java.util.*
import kotlin.collections.ArrayList

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

        val calander1 = Calendar.getInstance(Locale.getDefault())
        calander1.timeInMillis = date!!.toLong()
        val dateDisplay = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa",calander1)

        holder.dateTxv.text=dateDisplay

        if (image=="null"){
            holder.profileImv.setImageResource(R.drawable.ic_person)
        }else{
            holder.profileImv.setImageURI(Uri.parse(image))
        }

        holder.moreButton.setOnClickListener{
            showMoreOption(
                position,
                id,
                name,
                image,
                date,
                desc
            )
        }

        holder.mainCard.setOnClickListener {
            val intent = Intent(context,RecordsDetailActivity::class.java)
            intent.putExtra("RECORD_DATA",id)
            context!!.startActivity(intent)
        }

    }

    private fun showMoreOption(
        position: Int,
        id: String?,
        name: String?,
        image: String?,
        date: String,
        desc: String?
    ) {
        val options = arrayOf("Edit","Delete")
        val dialog:AlertDialog.Builder = AlertDialog.Builder(context!!)
        dialog.setItems(options) { dialog, which ->
            if(which==0){
                val intent = Intent(context!!,AddUpdateRecordActivity::class.java)
                intent.putExtra("ID",id)
                intent.putExtra("NAME",name)
                intent.putExtra("DESC",desc)
                intent.putExtra("IMAGE",image)
                intent.putExtra("DATE",date)
                intent.putExtra("isEditMode",true)
                context!!.startActivity(intent)
            }else{

            }
        }
        dialog.show()
    }
}