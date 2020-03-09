package com.example.parkingkotlin.activity.register.presenter

import android.app.Application
import android.util.Log
import com.example.parkingkotlin.activity.register.repository.RegisterRepository
import com.example.parkingkotlin.activity.register.repository.RegisterRepositoryImpl
import com.example.parkingkotlin.activity.register.view.RegisterActivityView
import com.example.parkingkotlin.database.entity.ClientEntity

class RegisterPresenterImpl(application: Application, private val registerActivityView: RegisterActivityView): RegisterPresenter {

    private val registerRepository: RegisterRepository = RegisterRepositoryImpl(application, this)

    override fun registerClient(clientEntity: ClientEntity) {

        registerActivityView.showProgress()
        registerRepository.saveUser(clientEntity)
    }

    override fun onSaveSuccess() {
        registerActivityView.hideProgress()
        registerActivityView.showSuccessMesage("Guardado con exito")
        registerActivityView.clearInputs()
    }

    override fun onSaveError(throwable: Throwable) {
        registerActivityView.hideProgress()
        registerActivityView.showErrorMessage(throwable)
    }
}