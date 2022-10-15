package com.pizza.application.data.repository.menu

import com.pizza.application.api.Result
import com.pizza.application.api.successed
import com.pizza.application.data.local.database.PizzaListEntity
import com.pizza.application.data.local.preferences.PreferenceStorage
import com.pizza.application.util.Constants
import java.util.*

import javax.inject.Inject

class PizzaListRepository @Inject constructor(
    private val pizzaListRemoteDataSource: PizzaListRemoteDataSource,
    private val pizzaListLocalDataSource: PizzaListLocalDataSource,
    private val preferenceStorage: PreferenceStorage) {

    val pizzasList = pizzaListLocalDataSource.pizzaList

    suspend fun loadPizzasLists() {
        pizzasList(Constants.PIZZAS_CATEGORY)
        pizzasList(Constants.DESSERTS_CATEGORY)
        pizzasList(Constants.DRINKS_CATEGORY)
    }

    suspend fun pizzasList(category: String) {
        when (val result = pizzaListRemoteDataSource.pizzaList(category)) {
            is Result.Success -> {
                if (result.successed) {
                    val pizzaList = result.data.let {
                        it.filter { item -> item.id.isNullOrEmpty().not() }
                            .map { pizza ->
                                PizzaListEntity(
                                    pizza.id ?: "",
                                    category,
                                    pizza.img,
                                    pizza.name,
                                    pizza.dsc,
                                    pizza.price,
                                    pizza.rate,
                                    pizza.country
                                )
                            }
                    }
                    pizzaListLocalDataSource.insertPizzasIntoDatabase(pizzaList)
                    preferenceStorage.timeLoadedAt = Date().time
                    Result.Success(true)
                } else {
                    Result.Error(Constants.GENERIC_ERROR)
                }
            }
            else -> result as Result.Error
        }
    }

    fun loadData(): Boolean {
        val lastLoadedTime = preferenceStorage.timeLoadedAt
        val currentTime = Date().time
        return currentTime - lastLoadedTime > 20 * 1000
    }
}