package com.example.parkingkotlin.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.parkingkotlin.R
import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.events.ClientEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class DetailClientDialog(val activity: Activity): AppCompatDialogFragment() {

    var clientName: TextView? = null
    var clientIdentification: TextView? = null
    var clientPhone: TextView? = null
    var clientPlaque: TextView? = null
    var clientRate: TextView? = null
    var clientStartDate: TextView? = null
    var clientDueDate: TextView? = null


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =  AlertDialog.Builder(activity)
        val view: View = activity.layoutInflater.inflate(R.layout.dialog_detail_client, null)

        builder.setView(view)

        val imageClose: ImageView = view.findViewById(R.id.detail_client_img_close)

        clientName = view.findViewById(R.id.detail_client_txt_name)
        clientIdentification = view.findViewById(R.id.detail_client_txt_identification)
        clientPhone = view.findViewById(R.id.detail_client_txt_phone)
        clientRate = view.findViewById(R.id.detail_client_txt_rate)
        clientPlaque = view.findViewById(R.id.detail_client_txt_plaque)
        clientStartDate = view.findViewById(R.id.detail_client_txt_start_date)
        clientDueDate = view.findViewById(R.id.detail_client_txt_due_date)

        imageClose.setOnClickListener{
            dismiss()
        }
        return builder.create()
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true)
    fun onEvent(clientEvent: ClientEvent){
        clientName?.text = clientEvent.clientEntity.clientName
        clientIdentification?.text = clientEvent.clientEntity.clientIdentification
        clientPhone?.text = clientEvent.clientEntity.clientPhone
        clientPlaque?.text = clientEvent.clientEntity.clientPlaque
        clientRate?.text = clientEvent.clientEntity.clientRate.toString()
    }
}