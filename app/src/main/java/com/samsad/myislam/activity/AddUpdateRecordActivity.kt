package com.samsad.myislam.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.samsad.myislam.R
import com.samsad.myislam.database.MyDBHelper
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_update_record.*

class AddUpdateRecordActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val CAMERA_REQUEST_CODE = 100
    private val STORAGE_REQUEST_CODE = 101

    private val IMAGE_CAMERA = 102
    private val IMAGE_GALLERY = 103

    private var cameraPer =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var storagePer = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private var actionbar: ActionBar? = null

    private var name: String? = null
    private var desc: String? = null

    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_record)

        actionbar = supportActionBar
        actionbar!!.title = "Add record"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowHomeEnabled(true)

        myDBHelper = MyDBHelper(this)

        camImv.setOnClickListener {
            showImagePicker()
        }

        submitBut.setOnClickListener {
            getTextFieldData()
        }
    }

    private fun getTextFieldData() {
        name = contentEdt.editableText.toString()
        desc = shortDescEdt.editableText.toString()
        val time = System.currentTimeMillis()
        val id = myDBHelper.insertRecord(
            name,
            "" + imageUri, desc, time.toString(), time.toString()
        )
        Toast.makeText(this, "At $id", Toast.LENGTH_LONG).show()
    }

    private fun showImagePicker() {
        val options = arrayOf("Camera", "Galley")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Image From")
        builder.setItems(options) { dialog, which ->
            if (which > 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickFromCamera()
                }
            } else {
                //gallary clicked
                if (!checkCStoragePermission()) {
                    requestStoragePermission()
                } else {
                    pickFromGallary()
                }
            }

        }
        builder.show()
    }

    private fun pickFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Image Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, IMAGE_CAMERA)
    }

    private fun pickFromGallary() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_GALLERY)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPer, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        val results = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val results2 = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        return results && results2
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePer, STORAGE_REQUEST_CODE)

    }

    private fun checkCStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val isCam = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val isStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (isCam && isStorage) {
                        pickFromCamera()
                    } else {
                        Toast.makeText(this, "Need proper permission", Toast.LENGTH_LONG).show()
                    }
                }

            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val isStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (isStorage) {
                        pickFromGallary()
                    } else {
                        Toast.makeText(this, "Need proper permission", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_CAMERA -> {
                    CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this)
                }
                IMAGE_GALLERY -> {
                    CropImage.activity(data!!.data)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    val resultUri = result.uri
                    imageUri = resultUri
                    camImv.setImageURI(resultUri)
                }
                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    val error = result.error
                    Toast.makeText(this, "Error :" + error.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
