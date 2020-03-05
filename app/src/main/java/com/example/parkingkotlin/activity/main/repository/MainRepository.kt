package com.example.parkingkotlin.activity.main.repository

interface MainRepository {

    fun getTotalClients()

    fun getTotalPaidClients()

    fun getTotalPendingClients()
}