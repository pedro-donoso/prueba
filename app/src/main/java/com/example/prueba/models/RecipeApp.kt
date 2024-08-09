package com.example.prueba.models

import android.app.Application
import com.example.prueba.db.AppDatabase

class RecipeApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}