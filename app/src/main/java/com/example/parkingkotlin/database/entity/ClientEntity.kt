package com.example.parkingkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = ClientEntity.TABLE_NAME)
class ClientEntity(
    @ColumnInfo(name = "name") @NotNull val clientName: String,
    @ColumnInfo(name = "identification") val clientIdentification: String,
    @ColumnInfo(name = "phone") val clientPhone: String,
    @ColumnInfo(name = "plaque") @NotNull val clientPlaque: String,
    @ColumnInfo(name = "rate") @NotNull val clientRate: Float,
    @ColumnInfo(name = "start_date") @NotNull val startDate: Date,
    @ColumnInfo(name = "due_date") @NotNull val dueDate: Date,
    @ColumnInfo(name = "active") @NotNull val clientActive: Int
){
    companion object{
        const val TABLE_NAME = "clients"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cliente")@NotNull val clientId: Int = 0
}