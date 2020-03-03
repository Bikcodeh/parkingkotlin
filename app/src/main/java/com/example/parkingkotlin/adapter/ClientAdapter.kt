package com.example.parkingkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingkotlin.R
import com.example.parkingkotlin.database.entity.ClientEntity

class ClientAdapter(val clientList: List<ClientEntity>): RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.clientList.size
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.clientName.text = this.clientList[position].clientName
        holder.clientPlaque.text = this.clientList[position].clientPlaque
    }

    class ClientViewHolder(view: View): RecyclerView.ViewHolder(view){

        val clientName: TextView = view.findViewById(R.id.item_list_client_txt_name)
        val clientPlaque: TextView = view.findViewById(R.id.item_list_client_txt_plaque)
    }
}