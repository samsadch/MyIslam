package com.samsad.myislam.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.samsad.myislam.model.ModelRecords

class MyDBHelper(context: Context) :
    SQLiteOpenHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME)
    }

    //Insert record to DB
    fun insertRecord(
        name: String?,
        image: String?,
        desc: String?,
        addedTime: String,
        updatedTime: String
    ): Long {
        //Get writable database because we want to write data
        val db = this.writableDatabase
        val values = ContentValues()
        //ID will be inserted automatically here because of auto increment
        values.put(Constants.C_NAME, name)
        values.put(Constants.C_IMAGE, image)
        values.put(Constants.C_DESC, desc)
        values.put(Constants.C_ADD_TIME, addedTime)
        values.put(Constants.C_UPDATED_TIME, updatedTime)
        //Returns record id of saved record
        val id = db.insert(Constants.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllRecords(orderBy: String): ArrayList<ModelRecords> {
        val records = ArrayList<ModelRecords>()
        val selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val modelRecord = ModelRecords(
                    "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_DESC)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIME)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIME))

                )
                records.add(modelRecord)
            }while (cursor.moveToNext())

        }
        db.close()
        return  records
    }


    fun searchRecords(query: String): ArrayList<ModelRecords> {
        val records = ArrayList<ModelRecords>()
        val selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_NAME +" LIKE  '%"+query+"%'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val modelRecord = ModelRecords(
                    "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_DESC)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIME)),
                    "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIME))

                )
                records.add(modelRecord)
            }while (cursor.moveToNext())

        }
        db.close()
        return  records
    }

}