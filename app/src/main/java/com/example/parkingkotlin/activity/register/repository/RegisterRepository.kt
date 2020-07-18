package com.example.parkingkotlin.activity.register.repository

import com.example.parkingkotlin.database.entity.ClientEntity
import com.example.parkingkotlin.model.ClientModel
import java.util.*

interface RegisterRepository {

    fun saveUser(clientEntity: ClientEntity)

    fun updateClient(client: ClientModel)

    fun updateUser()

    fun deleteUser()

    fun getUsers()
}