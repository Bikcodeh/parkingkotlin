package com.example.parkingkotlin.activity.register.repository

import android.app.Application
import android.util.Log
import com.example.parkingkotlin.activity.register.presenter.RegisterPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import com.example.parkingkotlin.database.entity.ClientEntity
import java.lang.Exception
import java.lang.IllegalArgumentException

class RegisterRepositoryImpl(application: Application, private val registerPresenter: RegisterPresenter): RegisterRepository {

    private val clientDao: ClientDao? = ClientsDatabase.getInstance(application)?.clientDao()

    override fun saveUser(clientEntity: ClientEntity) {

        var clientIdentification: String?
        val clientPlaque: String?

        if(clientDao != null){
            try {

                if(clientEntity.clientIdentification != ""){
                    clientIdentification = clientDao.getClientExistsIdentification(clientEntity.clientIdentification)
                    require(clientIdentification == null) { "Cedula ya registrada" }
                }

                clientPlaque = clientDao.getClientExistsPlaque(clientEntity.clientPlaque)

                require(clientPlaque == null) { "Placa ya registrada" }

                clientDao.insert(clientEntity)
                registerPresenter.onSaveSuccess()

            }catch (e: Exception){
                registerPresenter.onSaveError(e as Throwable)
            }
        }
    }
}