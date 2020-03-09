package com.example.parkingkotlin.activity.listClients.repository

interface ListClientsRepository {

    fun getAllClients()

    fun updateClientStatus(clientStatus: Int?, idClient: Int?)
}