package com.samsad.myislam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.samsad.myislam.R
import com.samsad.myislam.adapter.AdapterRecord
import com.samsad.myislam.database.Constants
import com.samsad.myislam.database.MyDBHelper
import kotlinx.android.synthetic.main.activity_records.*

class RecordsActivity : AppCompatActivity() {

    lateinit var dbHelper:MyDBHelper
    private val NEWEST_FIRST = Constants.C_ADD_TIME+" DESC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        dbHelper = MyDBHelper(this)
        loadRecord()

        addButton.setOnClickListener {
            val intent = Intent(this,AddUpdateRecordActivity::class.java)
            intent.putExtra("isEditMode",false)
            startActivity(intent)
        }
    }

    private fun loadRecord() {
        val adapterRecord = AdapterRecord(this,dbHelper.getAllRecords(NEWEST_FIRST))
        recordsRcv.adapter = adapterRecord
    }

    private fun searchRecords(query:String) {
        val adapterRecord = AdapterRecord(this,dbHelper.searchRecords(query))
        recordsRcv.adapter = adapterRecord
    }

    override fun onResume() {
        super.onResume()
        loadRecord()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView  = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    searchRecords(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    searchRecords(newText)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


}
