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

    @Query("UPDATE ${ClientEntity.TABLE_NAME} SET statusPayment = :activeStatus, dueDate = :date  WHERE id_cliente = :clientId")
    fun updateStatus(activeStatus: Int?, clientId: Int?, date: Date?): Int

    @Query("UPDATE ${ClientEntity.TABLE_NAME} SET name = :clientName, identification = :clientIdentification,  plaque = :clientPlaque, rate = :clientRate, phone = :clientPhone, statusPayment = :clientStatusPayment, startDate = :clientDate, active = :clientActive  WHERE id_cliente = :clientId")
    fun updateClient(clientName: String, clientIdentification: String, clientPlaque: String,
                     clientRate: Double, clientPhone: String, clientStatusPayment: Int?, clientId: Int?,
                     clientDate: Date?, clientActive: Int?): Int

    @Query("UPDATE ${ClientEntity.TABLE_NAME} SET statusPayment = 0 WHERE id_cliente in (:ids)")
    fun updateStatusClients(ids: List<Int>?)

    @Delete
    fun delete(clientEntity: ClientEntity)

    @Query("SELECT * FROM ${ClientEntity.TABLE_NAME} WHERE active = 1 ORDER BY name ")
    fun getClients(): LiveData<List<ClientEntity>>

    @Query("SELECT statusPayment FROM ${ClientEntity.TABLE_NAME} WHERE active = 1")
    fun getStatusPaymentList(): LiveData<List<Int>>

    @Query("SELECT * FROM ${ClientEntity.TABLE_NAME} WHERE dueDate = :dueDateClient")
    fun getClientsPending(dueDateClient: Date?): LiveData<List<ClientEntity>>
}