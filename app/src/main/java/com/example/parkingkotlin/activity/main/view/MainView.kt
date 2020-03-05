package com.example.parkingkotlin.activity.main.view

interface MainView {

    fun setTotalClients(value: Int?)
    fun setTotalPendingClients(value: Int?)
    fun setTotalPaidClients(value: Int?)

    fun showErrorMessage(throwable: Throwable)
}