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

    private val repository = MainRepositoryImpl(appCompatActivity, application, this)

    override fun getTotalClients() {
        repository.getTotalClients()
        repository.getTotalPaidClients()
        repository.getTotalPendingClients()
    }

    override fun getPendingClients(){
        val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).run { format(Date())}
        repository.getPendingClients(SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).run { parse(date)})
    }

    override fun onSuccessTotalClients(totalClients: Int?) {
        view.setTotalClients(totalClients)
    }

    override fun onErrorTotalClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessTotalPaidClients(totalClients: Int?) {
        view.setTotalPaidClients(totalClients)
    }

    override fun onErrorTotalPaidClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessTotalPendingClients(totalClients: Int?) {
        view.setTotalPendingClients(totalClients)
    }

    override fun onErrorTotalPendingClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessPendingClients(listPendingClients: List<ClientEntity>) {

        val listIds: MutableList<Int> = ArrayList()

        for (item in listPendingClients){
           listIds.add(item.clientId)
        }
        repository.updateStatusClients(listIds)
    }

}