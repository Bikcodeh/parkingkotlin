package com.example.parkingkotlin.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.parkingkotlin.R

class DetailClientDialog(val activity: Activity): AppCompatDialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =  AlertDialog.Builder(activity)
        val view: View = activity.layoutInflater.inflate(R.layout.dialog_detail_client, null)

        builder.setView(view)

        val imageClose: ImageView = view.findViewById(R.id.detail_client_img_close)
        imageClose.setOnClickListener{
            dismiss()
        }
        return builder.create()
    }
}