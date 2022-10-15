package com.pizza.application.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PizzaListDao {

    @Query("SELECT * FROM pizza_list")
    fun pizzaList(): LiveData<List<PizzaListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<PizzaListEntity>)

}