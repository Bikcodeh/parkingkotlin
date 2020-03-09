package com.example.parkingkotlin.activity.main.repository

import java.util.*

interface MainRepository {

    fun getTotalClients()

    fun getTotalPaidClients()

    fun getTotalPendingClients()

    fun getPendingClients(dueDate: Date?)

    fun updateStatusClients(ids: List<Int>?)
}