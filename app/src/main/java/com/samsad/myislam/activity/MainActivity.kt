package com.samsad.myislam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samsad.myislam.R
import com.samsad.myislam.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainRcv: RecyclerView
    var context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRcv = findViewById(R.id.mainRcv)
        mainRcv.layoutManager = GridLayoutManager(context, 2)
        var list: ArrayList<String> = ArrayList()
        list.add("Samsad")
        list.add("test")
        list.add("dummy")
        mainRcv.adapter = MainAdapter(context, list)


        addButton.setOnClickListener {
            startActivity(Intent(context, RecordsActivity::class.java))
        }


    }
}
