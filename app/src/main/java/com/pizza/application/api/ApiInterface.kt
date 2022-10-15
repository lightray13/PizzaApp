package com.pizza.application.api

import com.pizza.application.api.model.Pizza
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/pizzas")
    suspend fun pizzasList(): Response<List<Pizza>>

    @GET("/desserts")
    suspend fun dessertsList(): Response<List<Pizza>>

    @GET("/drinks")
    suspend fun drinksList(): Response<List<Pizza>>
}