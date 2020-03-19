package com.samsad.myislam.database

public object Constants {
    public const val DB_NAME = "MY_ISLAM_DB"
    const val DB_VERSION = 1
    const val TABLE_NAME = "MY_RECORDS_TABLE"

    //Columns
    const val C_ID = "ID"
    const val C_NAME = "NAME"
    const val C_IMAGE = "IMAGE"
    const val C_DESC = "DESC"
    const val C_ADD_TIME = "ADDED_TIME"
    const val C_UPDATED_TIME = "UPDATED_TIME"

    const val CREATE_TABLE = (
            "CREATE TABLE " + TABLE_NAME + "( "
                    + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + C_NAME + " TEXT,"
                    + C_IMAGE + " TEXT,"
                    + C_DESC + " TEXT,"
                    + C_ADD_TIME + " TEXT,"
                    + C_UPDATED_TIME + " TEXT"
                    + ")"
            )
}