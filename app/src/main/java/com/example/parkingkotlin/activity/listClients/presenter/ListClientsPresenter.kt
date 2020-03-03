package com.example.parkingkotlin.activity.listClients.presenter

import com.example.parkingkotlin.database.entity.ClientEntity

interface ListClientsPresenter {

    fun getClients()

    fun onSuccessGetClients(list: List<ClientEntity>)

    fun onErrorGetClients(throwable: Throwable)
}