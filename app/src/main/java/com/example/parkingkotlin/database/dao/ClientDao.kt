package com.example.parkingkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.parkingkotlin.database.entity.ClientEntity
import java.util.*

@Dao
interface ClientDao {

    @Insert
    fun insert(clientEntity: ClientEntity)

    @Update
    fun update(clientEntity: ClientEntity)

    @Query("UPDATE ${ClientEntity.TABLE_NAME} SET active = :activeStatus, dueDate = :date  WHERE id_cliente = :clientId")
    fun updateStatus(activeStatus: Int?, clientId: Int?, date: Date?): Int

    @Query("UPDATE ${ClientEntity.TABLE_NAME} SET active = 0 WHERE id_cliente in (:ids)")
    fun updateStatusClients(ids: List<Int>?)

    @Delete
    fun delete(clientEntity: ClientEntity)

    @Query("SELECT * FROM ${ClientEntity.TABLE_NAME} ORDER BY name")
    fun getClients(): LiveData<List<ClientEntity>>

    @Query("SELECT * FROM ${ClientEntity.TABLE_NAME} WHERE dueDate = :dueDateClient and active = 1")
    fun getClientsPending(dueDateClient: Date?): LiveData<List<ClientEntity>>

    @Query("SELECT COUNT(*) FROM ${ClientEntity.TABLE_NAME}")
    fun getTotalClients(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM ${ClientEntity.TABLE_NAME} WHERE active = :activeUser")
    fun getTotalClientsFiltering(activeUser: Int): LiveData<Int>

    @Query("SELECT identification FROM ${ClientEntity.TABLE_NAME} where identification = :clientIdentification")
    fun getClientExistsIdentification(clientIdentification: String?): String?

    @Query("SELECT plaque FROM ${ClientEntity.TABLE_NAME} where plaque = :clientPlaque")
    fun getClientExistsPlaque(clientPlaque: String?): String?

}