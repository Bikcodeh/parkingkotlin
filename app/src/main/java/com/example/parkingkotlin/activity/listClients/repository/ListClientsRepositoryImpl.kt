package com.example.parkingkotlin.activity.listClients.repository

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.parkingkotlin.activity.listClients.presenter.ListClientsPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import com.example.parkingkotlin.database.entity.ClientEntity
import java.lang.Exception
import java.util.*

class ListClientsRepositoryImpl(private val appCompatActivity: AppCompatActivity, application: Application, private val presenter: ListClientsPresenter): ListClientsRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()

    override fun getAllClients() {

        if(clientDao != null){
            try {
                kotlin.run {
                    clientDao.getClients().observe(appCompatActivity, Observer<List<ClientEntity>>{
                        presenter.onSuccessGetClients(it)
                    })
                }
            }catch (e: Exception){
                presenter.onErrorGetClients(e as Throwable)
                Log.d("ERROR OBTENIENDO USUARIOS", e.message.toString())
            }
        }
    }

    override fun updateClientPaid(clientStatus: Int?, idClient: Int?, date: Date?) {

        var updated: Int

        if(clientDao != null){
            try{
                kotlin.run {
                    updated = clientDao.updateStatus(clientStatus, idClient, date)
                    presenter.onSuccessUpdate(updated)
                    Log.d("ACTUALIZADO ***--*-", updated.toString())
                }
            }catch (exception: Exception){
                presenter.onErrorUpdate(exception)
            }
        }
    }
}