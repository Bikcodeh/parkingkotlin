package com.example.parkingkotlin.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import com.example.parkingkotlin.R
import com.example.parkingkotlin.events.ClientEvent
import com.example.parkingkotlin.events.ClientUpdateEvent
import com.google.android.material.button.MaterialButton
import es.dmoral.toasty.Toasty
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class DetailClientDialog(val activity: AppCompatActivity): AppCompatDialogFragment() {

    private lateinit var clientName: TextView
    private lateinit var clientIdentification: TextView
    private lateinit var clientPhone: TextView
    private lateinit var clientPlaque: TextView
    private lateinit var clientRate: TextView
    private lateinit var clientStartDate: TextView
    private lateinit var clientDueDate: TextView
    private lateinit var buttonPaid: MaterialButton
    private var clientId: Int? = null
    private val clientStatusPaid = 1
    private var dueDateUpdate: Date? = null
    private lateinit var calendar: Calendar

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
        clientDueDate.setTextColor(ContextCompat.getColor(view.context, R.color.brown_shadow))
        buttonPaid = view.findViewById(R.id.detail_client_btn_paid)

        calendar = Calendar.getInstance()

        buttonPaid.setOnClickListener{
            EventBus.getDefault().postSticky(ClientUpdateEvent(clientId, clientStatusPaid, dueDateUpdate))
            Toasty.success(it.context, getString(R.string.updated_client), Toast.LENGTH_SHORT).show()
            dismiss()
        }

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

    private fun dateFormat(date: Date): String{
        return SimpleDateFormat("dd-MM-yyyy").format(date)
    }

    @Subscribe(sticky = true)
    fun onEvent(clientEvent: ClientEvent){
        clientName.text = String.format("Nombre: %s", clientEvent.clientEntity.clientName)
        clientIdentification.text = String.format("Cedula: %s" ,clientEvent.clientEntity.clientIdentification)
        clientPhone.text = String.format("Celular: %s", clientEvent.clientEntity.clientPhone)
        clientPlaque.text = String.format("Placa: %s", clientEvent.clientEntity.clientPlaque)
        clientRate.text = String.format("Tarifa: %s",clientEvent.clientEntity.clientRate.toString())
        clientStartDate.text = String.format("Fecha de ingreso: %s", dateFormat(clientEvent.clientEntity.startDate))
        clientDueDate.text = String.format("Próximo corte: %s", dateFormat(clientEvent.clientEntity.dueDate))
        clientId = clientEvent.clientEntity.clientId

        if(clientEvent.clientEntity.clientActive == 0){
            buttonPaid.visibility = View.VISIBLE
            calendar.time = clientEvent.clientEntity.dueDate
            calendar.add(Calendar.MONTH, 1)
            dueDateUpdate = calendar.time

        }else{
            buttonPaid.visibility = View.GONE
        }
    }
}