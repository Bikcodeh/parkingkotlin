package com.example.parkingkotlin.activity.register.repository

import com.example.parkingkotlin.database.entity.ClientEntity

interface RegisterRepository {

    fun saveUser(clientEntity: ClientEntity)

    fun updateUser()

    fun deleteUser()

    fun getUsers()
}