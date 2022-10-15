package com.pizza.application.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pizza_list")
data class PizzaListEntity(
    @PrimaryKey val id: String,
    val category: String?,
    val img: String?,
    val name: String?,
    val dsc: String?,
    val price: Double?,
    val rate: Int?,
    val country: String?
)