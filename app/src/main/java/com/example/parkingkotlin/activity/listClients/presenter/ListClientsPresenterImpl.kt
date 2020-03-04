package com.example.parkingkotlin.activity.listClients.presenter

import android.app.Application
import com.example.parkingkotlin.activity.listClients.repository.ListClientsRepository
import com.example.parkingkotlin.activity.listClients.repository.ListClientsRepositoryImpl
import com.example.parkingkotlin.activity.listClients.view.ListClientsView
import com.example.parkingkotlin.database.entity.ClientEntity

class ListClientsPresenterImpl(application: Application, private val view: ListClientsView): ListClientsPresenter {

    private val repository: ListClientsRepository = ListClientsRepositoryImpl(application, this)

    override fun getClients() {
        view.showProgress()
        repository.getAllClients()
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