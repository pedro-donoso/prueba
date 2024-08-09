package com.example.prueba.services

import com.example.prueba.models.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET

//DEFINE UN CONTRATO PARA INTERACTUAR CON LA API
interface ApiService {

//    REALIZARA UNA SOLICITUD GET A LA RUTA RAIZ DE LA API
    @GET(".")
    suspend fun getRecipes(): Response<RecipesResponse>
}