package com.example.parkingkotlin.activity.listClients.view

import com.example.parkingkotlin.database.entity.ClientEntity

interface ListClientsView {

    fun showProgress()
    fun hideProgress()

    fun setDataToRecycler(list: List<ClientEntity>)

    fun showMessageError(throwable: Throwable)

    fun updateRecycler()

    fun hideEmptyClients()

    fun showEmptyClients()
}