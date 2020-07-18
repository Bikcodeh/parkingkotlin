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

    /*override fun getPendingClients(dueDate: Date?){
            try{
                kotlin.run {
                    clientDao?.getClientsPending(dueDate)?.observe(appcompat, Observer<List<ClientEntity>>{
                        presenter.onSuccessPendingClients(it)
                    })
                }
            }catch (exception: Exception){
                Log.d("ERROR OBTENIENDO CLIENTES PENDIENTES", exception.message.toString())
            }
    }*/

    override fun getClients(){
            try{
                kotlin.run {
                    clientDao?.getClients()?.observe(appcompat, Observer<List<ClientEntity>>{
                        presenter.onSuccessGetClients(it)
                    })
                }
            }catch (exception: Exception){
                Log.d("ERROR OBTENIENDO CLIENTES: ", exception.message.toString())
                presenter.onErrorGetClients(exception as Throwable)
            }
    }


    override fun updateStatusClients(ids: List<Int>?) {
        try{
            kotlin.run {
                clientDao?.updateStatusClients(ids)
                presenter.onSuccessUpdateClients(true)
            }
        }catch (exception: Exception){
            Log.d("ERROR ACTUALIZANDO USUARIOS: ", exception.message.toString())
            presenter.onErrorUpdateClients(exception as Throwable)
        }
    }

    override fun getStatusPaymentList(){
        try{
            kotlin.run {
                clientDao?.getStatusPaymentList()?.observe(appcompat, Observer<List<Int>>{
                    presenter.onSuccessStatusPaymentList(it)
                })
            }
        }catch (exception: Exception){
            Log.d("ERROR OBTENIENDO LISTA DE ESTADOS DE PAGO:", "${exception.message}")
            presenter.onErrorStatusPaymentList(exception as Throwable)
        }
    }
}