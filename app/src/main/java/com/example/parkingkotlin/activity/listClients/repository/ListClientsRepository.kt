package com.example.parkingkotlin.activity.listClients.repository

import java.util.*

interface ListClientsRepository {

    fun getAllClients()

    fun updateClientPaid(clientStatus: Int?, idClient: Int?, date: Date?)
}