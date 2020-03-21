package com.samsad.myislam.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.samsad.myislam.R
import com.samsad.myislam.database.Constants
import com.samsad.myislam.database.MyDBHelper
import kotlinx.android.synthetic.main.activity_record_details.*
import kotlinx.android.synthetic.main.list_item_records.*
import kotlinx.android.synthetic.main.list_item_records.descTxv
import kotlinx.android.synthetic.main.list_item_records.nameTxv
import kotlinx.android.synthetic.main.list_item_records.profileImv
import java.util.*

class RecordsDetailActivity : AppCompatActivity() {

    private var actionbar:ActionBar?=null
    private var dbHelper:MyDBHelper? = null

    private var recordId :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_details)

        actionbar=supportActionBar
        actionbar?.title="Records Details"
        actionbar?.setDisplayShowHomeEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = MyDBHelper(this)

        val intent = intent
        recordId = intent.getStringExtra("RECORD_DATA")
        showRecordDetails()

    }

    private fun showRecordDetails() {
        val selectQuery = "SELECT * FROM "+Constants.TABLE_NAME+" WHERE "+Constants.C_ID+"=\""+recordId+"\""
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery(selectQuery,null)

        if(cursor?.moveToFirst()!!){
            do{
               val id = cursor.getInt(cursor.getColumnIndex(Constants.C_ID))
               val name =  cursor.getString(cursor.getColumnIndex(Constants.C_NAME))
                val image =  cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE))
                val des = cursor.getString(cursor.getColumnIndex(Constants.C_DESC))
                val addTime = cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIME))
                val updateTime =  cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIME))

                val calander1 = Calendar.getInstance(Locale.getDefault())
                calander1.timeInMillis = addTime.toLong()
                val addTimeNew = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa",calander1)


                val calander2 = Calendar.getInstance(Locale.getDefault())
                calander2.timeInMillis = updateTime.toLong()
                val updatedTimeNew = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa",calander2)

                nameTxv.text = name
                descTxv.text=des
                createdTxv.text=addTimeNew
                updatedTxv.text=updatedTimeNew

                if(image==null){
                    userImv.setImageResource(R.drawable.ic_person)
                }else{
                    userImv.setImageURI(Uri.parse(image))
                }


            }while (cursor.moveToNext())
        }
        db.close()


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
