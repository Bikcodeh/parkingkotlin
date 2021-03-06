package com.example.parkingkotlin.database.db

import android.content.Context
import androidx.room.*
import com.example.parkingkotlin.database.converter.Converters
import com.example.parkingkotlin.database.dao.ClientDao
import com.example.parkingkotlin.database.entity.ClientEntity

@Database(entities = [ClientEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ClientsDatabase: RoomDatabase() {
    abstract fun clientDao(): ClientDao

    companion object{
        private const val DATABASE_NAME = "parkingApp"

        @Volatile
        private var INSTANCE: ClientsDatabase? = null

        fun getInstance(context: Context): ClientsDatabase?{
            INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ClientsDatabase::class.java,
                    DATABASE_NAME).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}