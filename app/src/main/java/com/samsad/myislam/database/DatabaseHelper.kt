package com.samsad.myislam.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    val DATABASE_NAME :String = "myislam.db"

    val TABLE_NAME :String = "Main.Table"
    val COL_1 :String = "HEADER"
    val COL_2 :String = "IMAGE"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table"+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,HEADER TEXT,IMAGE,TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME)
        onCreate(db)
    }

    fun inserData(header:String,image:String):Boolean{
        val db:SQLiteDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_1,header)
        contentValues.put(COL_2,image)
        db.insert(TABLE_NAME,null,contentValues)
        return true
    }

}