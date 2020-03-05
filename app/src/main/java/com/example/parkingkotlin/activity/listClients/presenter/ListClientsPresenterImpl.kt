package com.example.parkingkotlin.activity.listClients.presenter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingkotlin.activity.listClients.repository.ListClientsRepository
import com.example.parkingkotlin.activity.listClients.repository.ListClientsRepositoryImpl
import com.example.parkingkotlin.activity.listClients.view.ListClientsView
import com.example.parkingkotlin.database.entity.ClientEntity

class ListClientsPresenterImpl(appCompatActivity: AppCompatActivity, application: Application, private val view: ListClientsView): ListClientsPresenter {

    private val repository: ListClientsRepository = ListClientsRepositoryImpl(appCompatActivity, application, this)

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