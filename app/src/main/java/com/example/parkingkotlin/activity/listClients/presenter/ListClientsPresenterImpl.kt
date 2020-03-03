package com.example.parkingkotlin.activity.listClients.presenter

import com.example.parkingkotlin.activity.listClients.view.ListClientsView
import com.example.parkingkotlin.database.entity.ClientEntity

class ListClientsPresenterImpl(private val view: ListClientsView): ListClientsPresenter {

    override fun getClients() {
        view.showProgress()
    }

    override fun onSuccessGetClients(list: List<ClientEntity>) {
        view.hideProgress()
        view.setDataToRecycler(list)
    }

    override fun onErrorGetClients(throwable: Throwable) {
        view.hideProgress()
        view.showMessageError(throwable)
    }
}