package com.example.parkingkotlin.database.dao

import androidx.room.*
import com.example.parkingkotlin.database.entity.ClientEntity

@Dao
interface ClientDao {

    @Insert
    fun insert(clientEntity: ClientEntity)

    @Update
    fun update(clientEntity: ClientEntity)

    @Delete
    fun delete(clientEntity: ClientEntity)

    @Query("SELECT * FROM ${ClientEntity.TABLE_NAME} WHERE active = 1 ORDER BY name")
    fun getClients(): List<ClientEntity>

}