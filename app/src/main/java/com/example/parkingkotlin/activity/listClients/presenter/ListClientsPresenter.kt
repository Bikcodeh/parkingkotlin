package com.example.parkingkotlin.activity.listClients.presenter

import com.example.parkingkotlin.database.entity.ClientEntity

interface ListClientsPresenter {

    fun getClients()

    fun updateClientStatus(status: Int)

    fun onSuccessGetClients(list: List<ClientEntity>)

    fun onErrorGetClients(throwable: Throwable)

    fun onSuccessUpdate(updated: Int)

    fun onErrorUpdate(throwable: Throwable)

    fun registerEventBus()

    fun unRegisterEvent()
}