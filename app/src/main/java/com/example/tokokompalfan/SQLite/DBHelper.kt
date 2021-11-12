package com.example.tokokompalfan.SQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.tokokompalfan.Adapter.GudangAdapter
import com.example.tokokompalfan.Model.LaptopModel

val DATABASENAME = "tokokompalfan"
val TABLENAME = "laptop"
val COL_ID  = "id"
val COL_BRAND = "brand"
val COL_SERI = "seri"
val COL_HARGA = "harga"
val COL_STATUS = "status"
val COL_TAGS = "tags"


class DBHelper(val context: Context): SQLiteOpenHelper(context, DATABASENAME,null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_BRAND + " VARCHAR(20)," + COL_SERI + " VARCHAR(30), " +
                COL_HARGA + " INTEGER, " + COL_STATUS + " VARCHAR(20), " + COL_TAGS + " VARCHAR(50) )"
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(laptop: LaptopModel) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_BRAND, laptop.brand)
        contentValues.put(COL_SERI, laptop.seri)
        contentValues.put(COL_HARGA, laptop.harga)
        contentValues.put(COL_STATUS, laptop.status)
        contentValues.put(COL_TAGS, laptop.tags)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    fun readData(): ArrayList<LaptopModel> {
        val list: ArrayList<LaptopModel> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                val brand = result.getString(result.getColumnIndex(COL_BRAND))
                val seri = result.getString(result.getColumnIndex(COL_SERI))
                val harga = result.getString(result.getColumnIndex(COL_HARGA)).toInt()
                val status = result.getString(result.getColumnIndex(COL_STATUS))
                val tags = result.getString(result.getColumnIndex(COL_TAGS))
                val laptop = LaptopModel(id,brand,seri,harga,status,tags)
                list.add(laptop)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun deleteData(id: Int?){
        val db = this.readableDatabase
        db.execSQL("delete from ${TABLENAME} where ${COL_ID} = '"+id+"'")
    }

}