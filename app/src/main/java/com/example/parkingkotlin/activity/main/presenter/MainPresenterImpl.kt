package com.example.parkingkotlin.activity.main.presenter

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingkotlin.activity.main.repository.MainRepositoryImpl
import com.example.parkingkotlin.activity.main.view.MainView
import com.example.parkingkotlin.database.entity.ClientEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainPresenterImpl(appCompatActivity: AppCompatActivity, application: Application, private val view: MainView): MainPresenter {

    private var listIds: MutableList<Int> = ArrayList();
    private val repository = MainRepositoryImpl(appCompatActivity, application, this)

    override fun getStatusPaymentList() {
        repository.getStatusPaymentList()
    }

    override fun onSuccessStatusPaymentList(statusPaymentList: List<Int>?) {
        var totalPaid = 0
        var totalPending = 0

        view.setTotalClients(statusPaymentList?.size)

        if (statusPaymentList != null) {
            for(status in statusPaymentList){
                if(status == 1)
                    totalPaid += 1

                if(status == 0)
                    totalPending += 1
            }
            view.setTotalPaidClients(totalPaid)
            view.setTotalPendingClients(totalPending)
        }
    }

    override fun onErrorStatusPaymentList(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessPendingClients(listPendingClients: List<ClientEntity>) {

        /*val listIds: MutableList<Int> = ArrayList()
        val today = Date()

        for (item in listPendingClients){
            if(today >= listPendingClients[0].dueDate)
                listIds.add(item.clientId)
        }
        Log.d("LISTA DE VENCIDOS", listIds.toString())
        repository.updateStatusClients(listIds)*/
    }

    override fun onSuccessGetClients(listPendingClients: List<ClientEntity>) {
        if(listPendingClients.isNotEmpty()){
            val listIds: MutableList<Int> = ArrayList()
            val today = Date()

            for (item in listPendingClients){
                if(today >= listPendingClients[0].dueDate)
                    listIds.add(item.clientId)
            }
            this.listIds = listIds
        }
    }

    override fun onErrorGetClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessUpdateClients(updated: Boolean) {
        Log.d("Updated", "successfull")
    }

    override fun onErrorUpdateClients(throwable: Throwable) {
       view.showErrorMessage(throwable)
    }

    override fun getClients(){
        repository.getClients()
    }

    override fun updateClients() {
        repository.updateStatusClients(this.listIds)
    }
}