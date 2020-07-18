package com.example.parkingkotlin.activity.main.repository

import java.util.*

interface MainRepository {

    //fun getPendingClients(dueDate: Date?)

    fun getClients()

    fun updateStatusClients(ids: List<Int>?)

    fun getStatusPaymentList()
}