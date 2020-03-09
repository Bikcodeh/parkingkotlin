package com.example.parkingkotlin.activity.main.presenter

import com.example.parkingkotlin.database.entity.ClientEntity

interface MainPresenter {

    fun getTotalClients()

    fun getPendingClients()

    fun onSuccessTotalClients(totalClients: Int?)
    fun onErrorTotalClients(throwable: Throwable)

    fun onSuccessTotalPaidClients(totalClients: Int?)
    fun onErrorTotalPaidClients(throwable: Throwable)

    fun onSuccessTotalPendingClients(totalClients: Int?)
    fun onErrorTotalPendingClients(throwable: Throwable)

    fun onSuccessPendingClients(listPendingClients: List<ClientEntity>)
}