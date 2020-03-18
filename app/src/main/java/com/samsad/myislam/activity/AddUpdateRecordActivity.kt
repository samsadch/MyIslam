package com.samsad.myislam.activity

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.samsad.myislam.R
import kotlinx.android.synthetic.main.activity_add_update_record.*

class AddUpdateRecordActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 100
    private val STORAGE_REQUEST_CODE = 101

    private val IMAGE_CAMERA = 102
    private val IMAGE_GALLERY = 103

    private lateinit var cameraPer:ArrayList<String>
    private lateinit var storagePer:ArrayList<String>

    private var actionbar:ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_record)

        actionbar = supportActionBar
        actionbar!!.title = "Add record"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        cameraPer = arrayListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        storagePer = arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        camImv.setOnClickListener {
            showImagePicker()
        }

        submitBut.setOnClickListener {

        }
    }

    private fun showImagePicker() {
        val options = arrayOf("Camera","Galley")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Image From")
        builder.setItems(options){dialog, which ->
            if (which>0){
                if(!checkCameraPermission()){
                    requestCameraPermission()
                }else{
                    pickFromCamera()
                }
            }else{
                //gallary clicked
                if(!checkCStoragePermission()){
                    requestStoragePermission()
                }else{
                    pickFromGallary()
                }
            }

        }

    }

    private fun pickFromCamera(){

    }

    private fun pickFromGallary(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_GALLERY)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }

    private fun  requestCameraPermission(){

    }

    private fun checkCameraPermission():Boolean{

    }

    private fun  requestStoragePermission(){

    }

    private fun checkCStoragePermission():Boolean{

    }
}
