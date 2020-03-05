package com.example.parkingkotlin.activity.main.repository

import android.app.Application
import android.util.Log
import com.example.parkingkotlin.activity.main.presenter.MainPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import java.lang.Exception

class MainRepositoryImpl(application: Application, private val presenter: MainPresenter): MainRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()

    override fun getTotalClients() {

        var totalClients: Int?

        try {

            kotlin.run {
                totalClients = clientDao?.getTotalClients()
            }
            presenter.onSuccessTotalClients(totalClients)
            Log.d("TOTAL DE CLIENTES: ", ""+ totalClients)
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES: ", ""+ exception.message)
            presenter.onErrorTotalClients(exception as Throwable)
        }

    }

    override fun getTotalPaidClients() {
        var totalPaidClients: Int?

        try {

            kotlin.run {
                totalPaidClients = clientDao?.getTotalClientsFiltering(1)
            }
            presenter.onSuccessTotalPaidClients(totalPaidClients)
            Log.d("TOTAL DE CLIENTES PAGOS: ", ""+ totalPaidClients)
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES PAGOS: ", ""+ exception.message)
            presenter.onErrorTotalPaidClients(exception as Throwable)
        }

    }

    override fun getTotalPendingClients() {
        var totalPaidClients: Int?

        try {

            kotlin.run {
                totalPaidClients = clientDao?.getTotalClientsFiltering(0)
            }
            presenter.onSuccessTotalPendingClients(totalPaidClients)
            Log.d("TOTAL DE CLIENTES PENDIENTES: ", ""+ totalPaidClients)
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES PENDIENTES: ", ""+ exception.message)
            presenter.onErrorTotalPendingClients(exception as Throwable)
        }

    }
}