package com.example.parkingkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingkotlin.R
import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.events.ClientEvent
import com.example.parkingkotlin.util.DetailClientDialog
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

class ClientAdapter(val activity: AppCompatActivity, private var clientList: List<ClientEntity>): RecyclerView.Adapter<ClientAdapter.ClientViewHolder>(), View.OnClickListener {

    private val listener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.clientList.size
    }

    fun filterList(filteredList: ArrayList<ClientEntity>) {
        this.clientList = filteredList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.clientName.text = this.clientList[position].clientName
        holder.clientPlaque.text = this.clientList[position].clientPlaque
        if(this.clientList[position].statusPayment == 1){
            holder.clientStatus.text = holder.itemView.resources.getString(R.string.up_to_date)
        }else{
            holder.clientStatus.text = holder.itemView.resources.getString(R.string.pending_payment)
            holder.clientStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.brown_shadow))
        }
        holder.itemView.setOnClickListener{
            EventBus.getDefault().postSticky( ClientEvent(this.clientList[position]))
            val detailClientDialog = DetailClientDialog(activity)
            detailClientDialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, "Dialog")
            detailClientDialog.isCancelable = false
        }
    }

    override fun onClick(v: View?) {
        listener?.onClick(v)
    }

    class ClientViewHolder(view: View): RecyclerView.ViewHolder(view){

        val clientName: TextView = view.findViewById(R.id.item_list_client_txt_name)
        val clientPlaque: TextView = view.findViewById(R.id.item_list_client_txt_plaque)
        val clientStatus: TextView = view.findViewById(R.id.item_list_client_status)
    }
}