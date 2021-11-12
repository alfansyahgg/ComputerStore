package com.example.tokokompalfan.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tokokompalfan.Model.LaptopModel
import com.example.tokokompalfan.R
import com.example.tokokompalfan.SQLite.DBHelper
import com.example.tokokompalfan.databinding.GudangItemsBinding
import com.google.android.material.chip.Chip

class GudangAdapter(private var laptopList: ArrayList<LaptopModel>,private val context: Context):
    RecyclerView.Adapter<GudangAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding     = GudangItemsBinding.bind(view)
        val cv_item     = binding.cvItem
        val tv_title    = binding.tvTitle
        val tv_harga    = binding.tvHarga
        val cg_tags     = binding.cgTags
        val ll_status   = binding.llStatus
        val tv_status   = binding.tvStatus

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GudangAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).
                inflate(R.layout.gudang_items,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GudangAdapter.MyViewHolder, position: Int) {
        holder.tv_title.text    = laptopList.get(position).brand + " " + laptopList.get(position).seri
        holder.tv_harga.text    = "Rp. ${laptopList.get(position).harga.toString()}"
        holder.tv_status.text   = laptopList.get(position).status

        var status = laptopList.get(position).status
        if(status == "Available"){
           holder.ll_status.setBackgroundResource(R.drawable.button_2)
        }else{
           holder.ll_status.setBackgroundResource(R.drawable.button_3)
        }

        val arrTags             = laptopList.get(position).tags?.split(",")?.toTypedArray()
        if (arrTags != null) {
            for (tags in arrTags){
                val chip: Chip = Chip(context)
                chip.text = tags
                chip.ensureAccessibleTouchTarget(50)
                holder.cg_tags.addView(chip)
            }
        }

        holder.cv_item.setOnLongClickListener {
            val mBuilder = AlertDialog.Builder(context)

            mBuilder.setMessage("Ingin Menghapus Data?")
                .setCancelable(false)
                .setPositiveButton("Ya",DialogInterface.OnClickListener{
                    dialogInterface, i ->
                    val dbHelper = DBHelper(context)
                    dbHelper.deleteData(laptopList.get(position).id)

                    laptopList.removeAt(position)
                    notifyDataSetChanged()


                    mBuilder.show().dismiss()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })

            mBuilder.setOnDismissListener {
                notifyDataSetChanged()
            }

            mBuilder.setTitle("Hapus Data")
            mBuilder.show()

            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return laptopList.size
    }


}