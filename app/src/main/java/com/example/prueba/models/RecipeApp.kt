package com.example.prueba.models

//IMPORTACIONES
import android.app.Application
import com.example.prueba.db.AppDatabase

//PARA OBTENER LA INSTANCIA BD
class RecipeApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}