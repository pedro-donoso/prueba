package com.example.prueba.retrofit

//IMPORTACIONES
import com.example.prueba.services.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//API QUE SE VA A CONSUMIR
object RetrofitClient {
    private const val BASE_URL = "https://curso-android-56-1.vercel.app"
    private val client = OkHttpClient.Builder()

        //PARA CONEXIONES LENTAS
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)

//        CAPTURA COOKIES RESPUESTA
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