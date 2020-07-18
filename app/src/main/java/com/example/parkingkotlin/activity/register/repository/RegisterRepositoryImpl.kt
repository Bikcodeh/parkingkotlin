package com.example.parkingkotlin.activity.register.repository

import android.app.Application
import android.util.Log
import com.example.parkingkotlin.activity.register.presenter.RegisterPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.model.ClientModel
import java.lang.Exception

class RegisterRepositoryImpl(application: Application, private val registerPresenter: RegisterPresenter): RegisterRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()

    override fun saveUser(clientEntity: ClientEntity) {

        if(clientDao != null){
            try {
                clientDao.insert(clientEntity)
                registerPresenter.onSaveSuccess()
            }catch (e: Exception){
                registerPresenter.onSaveError(e as Throwable)
            }
        }
    }

    override fun updateClient(client: ClientModel) {
     try {
         kotlin.run {
             clientDao?.updateClient(client.clientName, client.clientIdentification,
                 client.clientPlaque, client.clientRate, client.clientPhone, client.statusPayment,
                 client.clientId, client.startDate, client.clientActive)
             registerPresenter.onSuccessUpdate(true)
         }
     }catch (exception: Exception){
         Log.d("ERROR ACTUALIZANDO USUARIO ", "${exception.message}")
         registerPresenter.onUpdateError(exception as Throwable)
     }
    }

    override fun updateUser() {

    }

    override fun deleteUser() {

    }

    override fun getUsers() {

    }
}