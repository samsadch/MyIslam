package com.samsad.myislam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.samsad.myislam.R
import com.samsad.myislam.adapter.AdapterRecord
import com.samsad.myislam.database.Constants
import com.samsad.myislam.database.MyDBHelper
import kotlinx.android.synthetic.main.activity_records.*

class RecordsActivity : AppCompatActivity() {

    lateinit var dbHelper:MyDBHelper
    private val NEWEST_FIRST = Constants.C_ADD_TIME+" DESC"
    private val OLDEST_FIRST = Constants.C_ADD_TIME+" ASC"
    private val TITLE_ASC = Constants.C_NAME+" ASC"
    private val TITLE_DESC = Constants.C_NAME+" DESC"

    private var recentOrder:String = NEWEST_FIRST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        dbHelper = MyDBHelper(this)
        loadRecord(NEWEST_FIRST)

        addButton.setOnClickListener {
            val intent = Intent(this,AddUpdateRecordActivity::class.java)
            intent.putExtra("isEditMode",false)
            startActivity(intent)
        }
    }

    private fun loadRecord(orderBy:String) {
        recentOrder = orderBy
        val adapterRecord = AdapterRecord(this,dbHelper.getAllRecords(orderBy))
        recordsRcv.adapter = adapterRecord
    }

    private fun searchRecords(query:String) {
        val adapterRecord = AdapterRecord(this,dbHelper.searchRecords(query))
        recordsRcv.adapter = adapterRecord
    }

    public override fun onResume() {
        super.onResume()
        loadRecord(recentOrder)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if( id == R.id.action_sort){
            sortDialog()
        }else if (id == R.id.action_delete_all){
            dbHelper.deleteAll()
            onResume()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortDialog() {
        var options = arrayOf("Title Ascending","Title Descending","Newest","Oldest")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort By")
            .setItems(options){_,which->
                when (which) {
                    0 -> {
                        loadRecord(TITLE_ASC)
                    }
                    1 -> {
                        loadRecord(TITLE_DESC)
                    }
                    2 -> {
                        loadRecord(NEWEST_FIRST)
                    }
                    3 -> {
                        loadRecord(OLDEST_FIRST)
                    }
                }
            }
    }


}
