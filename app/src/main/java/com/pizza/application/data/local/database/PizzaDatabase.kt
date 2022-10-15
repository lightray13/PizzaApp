package com.pizza.application.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PizzaListEntity::class], version = 1, exportSchema = false)
abstract class PizzaDatabase: RoomDatabase() {
    abstract fun pizzaListDao(): PizzaListDao

    companion object {
        fun buildDatabase(context: Context): PizzaDatabase {
            return Room.databaseBuilder(context, PizzaDatabase::class.java, "Pizza").build()
        }
    }
}