package com.example.parkingkotlin.activity.main.repository

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.parkingkotlin.activity.main.presenter.MainPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import com.example.parkingkotlin.database.entity.ClientEntity
import java.lang.Exception
import java.util.*

class MainRepositoryImpl(val appcompat: AppCompatActivity, val application: Application, private val presenter: MainPresenter): MainRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()

    override fun getTotalClients() {
        try {
            kotlin.run {
                clientDao?.getTotalClients()?.observe(appcompat, Observer<Int>{
                    presenter.onSuccessTotalClients(it)
                    Log.d("MAIN TOTAL CLIENTS", it.toString())
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
                   Log.d("MAIN PAID CLIENTS", it.toString())
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
                    Log.d("MAIN PENDING CLIENTS", it.toString())
                })
            }
        }catch (exception: Exception){
            Log.d("ERROR CON LOS CLIENTES PENDIENTES: ", ""+ exception.message)
            presenter.onErrorTotalPendingClients(exception as Throwable)
        }
    }

    override fun getPendingClients(dueDate: Date?){
            try{
                kotlin.run {
                    clientDao?.getClientsPending(dueDate)?.observe(appcompat, Observer<List<ClientEntity>>{
                        presenter.onSuccessPendingClients(it)
                    })
                }
            }catch (exception: Exception){
                Log.d("ERROR OBTENIENDO CLIENTES PENDIENTES", exception.message.toString())
            }
    }

    override fun updateStatusClients(ids: List<Int>?) {
        try{
            kotlin.run {
                clientDao?.updateStatusClients(ids)
            }
        }catch (exception: Exception){
            Log.d("ERROR ACTUALIZANDO USUARIOS: ", exception.message.toString())
        }
    }
}