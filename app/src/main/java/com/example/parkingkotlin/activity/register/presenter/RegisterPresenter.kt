package com.example.parkingkotlin.activity.register.presenter

interface RegisterPresenter {

    fun registerClient()

    fun onSaveSuccess()

    fun onSaveError(throwable: Throwable)
}