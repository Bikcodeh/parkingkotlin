package com.example.parkingkotlin.database.dao

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM ${ClientEntity.TABLE_NAME} ORDER BY name")
    fun getClients(): LiveData<List<ClientEntity>>

    @Query("SELECT COUNT(*) FROM ${ClientEntity.TABLE_NAME}")
    fun getTotalClients(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM ${ClientEntity.TABLE_NAME} WHERE active = :activeUser")
    fun getTotalClientsFiltering(activeUser: Int): LiveData<Int>

}