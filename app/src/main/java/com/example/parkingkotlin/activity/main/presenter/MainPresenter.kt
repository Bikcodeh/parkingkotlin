package com.example.parkingkotlin.activity.main.presenter

import com.example.parkingkotlin.database.entity.ClientEntity

interface MainPresenter {

    fun getClients()

    fun updateClients()
    fun getStatusPaymentList()

    fun onSuccessStatusPaymentList(statusPaymentList: List<Int>?)
    fun onErrorStatusPaymentList(throwable: Throwable)

    fun onSuccessPendingClients(listPendingClients: List<ClientEntity>)

    fun onSuccessGetClients(listPendingClients: List<ClientEntity>)
    fun onErrorGetClients(throwable: Throwable)

    fun onSuccessUpdateClients(updated: Boolean)
    fun onErrorUpdateClients(throwable: Throwable)
}