package com.example.tokokompalfan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import com.example.tokokompalfan.Model.LaptopModel
import com.example.tokokompalfan.SQLite.DBHelper
import com.example.tokokompalfan.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tagList = ArrayList<String>()
    private val TAG: String = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Database
        val db = DBHelper(applicationContext)

        //Text Input Layout
        val textBrand   = binding.textBrand
        val textHarga   = binding.textHarga
        val textSeri    = binding.textSeri

        val items = listOf("Asus", "Acer", "Lenovo", "Dell", "Zyrex")
        val adapter = ArrayAdapter(applicationContext, R.layout.list_brand, items)
        (textBrand.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //TextView
        val tv_brand    = binding.tvBrand
        val tv_seri     = binding.tvSeri
        val tv_harga    = binding.tvHarga

        //Radio
        val rg_status       = binding.rgStatus
        val rb_available    = binding.rbAvailable
        val rb_po           = binding.rbPo

        //Chip
        val cg_tag      = binding.cgTag

        //Button
        val btn_submit  = binding.btnSubmit

        btn_submit.setOnClickListener {
            val brand   = tv_brand.text.toString()
            val seri    = tv_seri.text.toString()
            val price   = tv_harga.text.toString()

            if (brand.isEmpty()){
                textBrand.error = "Missing Brand Value"
                return@setOnClickListener
            }
            if(seri.isEmpty()){
                textSeri.error = "Missing Seri Value"
                return@setOnClickListener
            }
            if(price == null){
                textHarga.error = "Missing Harga Value"
                return@setOnClickListener
            }


            val harga   = Integer.parseInt(tv_harga.text.toString())

            binding.tvBrand.setText("")
            binding.tvSeri.setText("")
            binding.tvHarga.setText("")
            tagList.clear()

            val id_status   = rg_status.checkedRadioButtonId
            val status      = findViewById<RadioButton>(id_status).text.toString()
            val chips       = cg_tag.checkedChipIds

            var string_tags = ""
            for(chip in chips){
                tagList.add(findViewById<Chip>(chip).text.toString())
                string_tags += findViewById<Chip>(chip).text.toString() + ","
            }
            string_tags = string_tags.toString().dropLast(1)
            db.insertData(LaptopModel(null,brand,seri,harga,status,string_tags))

            val intent = Intent(this,GudangActivity::class.java)
            startActivity(intent)

        }

        setupMenu()
    }

    fun setupMenu(){
        val tb_main = binding.tbMain
        tb_main.inflateMenu(R.menu.menu_1)

        tb_main.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_pindah -> {
                    val intent = Intent(this,GudangActivity::class.java)
                    startActivity(intent)
                }
            }

            return@setOnMenuItemClickListener false
        }
    }

}