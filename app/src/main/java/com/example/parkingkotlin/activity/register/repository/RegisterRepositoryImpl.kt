package com.example.parkingkotlin.activity.register.repository

import android.app.Application
import android.util.Log
import com.example.parkingkotlin.activity.register.presenter.RegisterPresenter
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.db.ClientsDatabase
import com.example.parkingkotlin.database.entity.ClientEntity
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

    override fun updateUser() {

    }

    override fun deleteUser() {

    }

    override fun getUsers() {

    }
}