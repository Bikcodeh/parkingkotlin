package com.example.parkingkotlin.activity.register.presenter

import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.model.ClientModel

interface RegisterPresenter {

    fun registerClient(clientEntity: ClientEntity)

    fun updateClient(client: ClientModel)

    fun onSaveSuccess()

    fun onSuccessUpdate(update: Boolean)
    fun onUpdateError(throwable: Throwable)

    fun onSaveError(throwable: Throwable)
}