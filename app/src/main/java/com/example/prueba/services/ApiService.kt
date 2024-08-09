package com.example.prueba.services

import com.example.prueba.models.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getRecipes(): Response<RecipesResponse>


}