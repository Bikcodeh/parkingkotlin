package com.example.parkingkotlin.activity.register.presenter

import com.example.parkingkotlin.database.entity.ClientEntity

interface RegisterPresenter {

    fun registerClient(clientEntity: ClientEntity)

    fun onSaveSuccess()

    fun onSaveError(throwable: Throwable)
}