package com.pizza.application.data.repository.menu

import com.google.gson.Gson
import com.pizza.application.api.ApiInterface
import com.pizza.application.api.Result
import com.pizza.application.api.model.GenericResponse
import com.pizza.application.api.model.Pizza
import com.pizza.application.util.Constants
import java.lang.Exception
import javax.inject.Inject

class PizzaListRemoteDataSource @Inject constructor(private val service: ApiInterface) {

    suspend fun pizzaList(category: String): Result<List<Pizza>> {
        try {
            val response = when(category) {
                Constants.PIZZAS_CATEGORY -> service.pizzasList()
                Constants.DESSERTS_CATEGORY -> service.dessertsList()
                Constants.DRINKS_CATEGORY -> service.drinksList()
                else -> service.pizzasList()
            }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            } else if (response.errorBody() != null) {
                try {
                    val errorBody = Gson().fromJson(response.errorBody()?.charStream(), GenericResponse::class.java)
                    return Result.Error(errorBody?.message ?: Constants.GENERIC_ERROR)
                } catch (e: Exception) {
                    return Result.Error(Constants.GENERIC_ERROR)
                }
            }
        } catch (e: Exception) {
            return Result.Error(e.message ?: e.toString())
        }
        return Result.Error(Constants.GENERIC_ERROR)
    }
}