package com.example.parkingkotlin.activity.main.presenter

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingkotlin.activity.main.repository.MainRepositoryImpl
import com.example.parkingkotlin.activity.main.view.MainView

class MainPresenterImpl(appCompatActivity: AppCompatActivity, application: Application, private val view: MainView): MainPresenter {


    private val repository = MainRepositoryImpl(appCompatActivity, application, this)

    override fun getTotalClients() {
        repository.getTotalClients()
        repository.getTotalPaidClients()
        repository.getTotalPendingClients()
    }

    override fun onSuccessTotalClients(totalClients: Int?) {
        view.setTotalClients(totalClients)
    }

    override fun onErrorTotalClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessTotalPaidClients(totalClients: Int?) {
        view.setTotalPaidClients(totalClients)
    }

    override fun onErrorTotalPaidClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }

    override fun onSuccessTotalPendingClients(totalClients: Int?) {
        view.setTotalPendingClients(totalClients)
    }

    override fun onErrorTotalPendingClients(throwable: Throwable) {
        view.showErrorMessage(throwable)
    }
}