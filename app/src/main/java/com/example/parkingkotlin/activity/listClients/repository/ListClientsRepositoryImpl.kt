package com.example.parkingkotlin.activity.listClients.repository

import android.app.Application
import android.util.Log
import com.example.parkingkotlin.activity.listClients.presenter.ListClientsPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import com.example.parkingkotlin.database.entity.ClientEntity
import java.lang.Exception

class ListClientsRepositoryImpl(application: Application ,private val presenter: ListClientsPresenter): ListClientsRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()
    private val statusActive = 1

    override fun getAllClients() {

        val list: List<ClientEntity>

        if(clientDao != null){
            try {
                list = clientDao.getClients()
                presenter.onSuccessGetClients(list)
                Log.d("LISTA DE CLIENTES", list.toString())
            }catch (e: Exception){
                presenter.onErrorGetClients(e as Throwable)
                Log.d("ERROR OBTENIENDO USUARIOS", e.message.toString())
            }
        }
    }

    override fun updateClientStatus(idClient: Int) {

        var updated: Int

        if(clientDao != null){
            try{
                kotlin.run {
                    updated = clientDao.updateStatus(statusActive, idClient)
                    presenter.onSuccessUpdate(updated)
                    Log.d("ACTUALIZADO ***--*-", updated.toString())
                }
            }catch (exception: Exception){
                presenter.onErrorUpdate(exception)
            }
        }
    }
}