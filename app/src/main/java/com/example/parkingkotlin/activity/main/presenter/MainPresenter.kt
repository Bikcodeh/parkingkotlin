package com.example.parkingkotlin.activity.main.presenter

interface MainPresenter {

    fun getTotalClients()

    fun onSuccessTotalClients(totalClients: Int?)
    fun onErrorTotalClients(throwable: Throwable)

    fun onSuccessTotalPaidClients(totalClients: Int?)
    fun onErrorTotalPaidClients(throwable: Throwable)

    fun onSuccessTotalPendingClients(totalClients: Int?)
    fun onErrorTotalPendingClients(throwable: Throwable)

}