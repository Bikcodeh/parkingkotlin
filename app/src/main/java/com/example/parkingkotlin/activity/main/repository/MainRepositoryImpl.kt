package com.example.parkingkotlin.activity.main.repository

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.parkingkotlin.activity.main.presenter.MainPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import java.lang.Exception

class MainRepositoryImpl(val appcompat: AppCompatActivity, val application: Application, private val presenter: MainPresenter): MainRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()

    override fun getTotalClients() {
        try {
            kotlin.run {
                clientDao?.getTotalClients()?.observe(appcompat, Observer<Int>{
                    presenter.onSuccessTotalClients(it)
                })
            }
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES: ", ""+ exception.message)
            presenter.onErrorTotalClients(exception as Throwable)
        }
    }

    override fun getTotalPaidClients() {

        try {
            kotlin.run {
               clientDao?.getTotalClientsFiltering(1)?.observe(appcompat, Observer<Int>{
                   presenter.onSuccessTotalPaidClients(it)
               })
            }
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES PAGOS: ", ""+ exception.message)
            presenter.onErrorTotalPaidClients(exception as Throwable)
        }
    }

    override fun getTotalPendingClients() {

        try {
            kotlin.run {
                clientDao?.getTotalClientsFiltering(0)?.observe(appcompat, Observer<Int>{
                    presenter.onSuccessTotalPendingClients(it)
                })
            }
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES PENDIENTES: ", ""+ exception.message)
            presenter.onErrorTotalPendingClients(exception as Throwable)
        }
    }
}