package com.example.prueba.retrofit

import com.example.prueba.services.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://curso-android-56-1.vercel.app"
    private val client = OkHttpClient.Builder()

        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

        .addInterceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            if (originalResponse.headers("Set-Cookie").isNotEmpty())  {
                val cookies = originalResponse.headers("Set-Cookie")
                println("Cookies: $cookies")
            }
            originalResponse
        }
        .build()
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}