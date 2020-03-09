package com.example.parkingkotlin.activity.register.view

interface RegisterActivityView {

    fun showProgress()
    fun hideProgress()

    fun showSuccessMesage(message: String)
    fun showErrorMessage(throwable: Throwable)

    fun clearInputs()
}