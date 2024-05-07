package com.example.chataplikacijapmu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chataplikacijapmu.databinding.PorukaBinding

class TextAdapter(

    private val textList:ArrayList<Poruka>,
    private val th:Context

):RecyclerView.Adapter<TextAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextAdapter.ViewHolder {
        val v = PorukaBinding.inflate(LayoutInflater.from(th), parent,false)
        return ViewHolder (v)
    }

    override fun onBindViewHolder(holder: TextAdapter.ViewHolder, position: Int) {
        holder.bindItem(textList[position],th)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    class ViewHolder(private var itemBinding: PorukaBinding):
            RecyclerView.ViewHolder(itemBinding.root){
                fun bindItem(poruka: Poruka,th:Context){
                    itemBinding.id.text=poruka.id.toString()
                }
            }
}