package com.pizza.application.data.repository.menu

import androidx.lifecycle.LiveData
import com.pizza.application.data.local.database.PizzaDatabase
import com.pizza.application.data.local.database.PizzaListEntity
import javax.inject.Inject

class PizzaListLocalDataSource @Inject constructor(private val database: PizzaDatabase) {

    val pizzaList: LiveData<List<PizzaListEntity>> = database.pizzaListDao().pizzaList()

    suspend fun insertPizzasIntoDatabase(list: List<PizzaListEntity>) {
        if (list.isNotEmpty()) {
            database.pizzaListDao().insert(list)
        }
    }
}