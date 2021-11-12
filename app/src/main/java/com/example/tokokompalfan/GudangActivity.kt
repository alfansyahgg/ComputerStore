package com.example.tokokompalfan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokokompalfan.Adapter.GudangAdapter
import com.example.tokokompalfan.Model.LaptopModel
import com.example.tokokompalfan.SQLite.DBHelper
import com.example.tokokompalfan.databinding.ActivityGudangBinding

class GudangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGudangBinding
    private val laptopList = ArrayList<LaptopModel>()
    private val TAG = "Gudang"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGudangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Database
        val db  = DBHelper(this)

        for(laptops in db.readData()){
         laptopList.add(laptops)
        }
        val rv_gudang = binding.rvGudang

        val gudangAdapter = GudangAdapter(laptopList, this)
        val layoutManager = LinearLayoutManager(this)
        with(rv_gudang) {
            setLayoutManager(layoutManager)
            adapter = gudangAdapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
        gudangAdapter.notifyDataSetChanged()
    }
}