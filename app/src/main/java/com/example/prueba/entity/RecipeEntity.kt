package com.example.prueba.entity

//IMPORTACIONES
import androidx.room.Entity
import androidx.room.PrimaryKey

//PROPIEDADES CAMPOS DE LA RECETA
@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var tags: String = "",
    var photoUrl: String = "",
    var ingredients: String = "",
    var directions: String = "",
    var totalTime: Long = 0,
    var cuisine: String = "",
    var rating: Double = 0.0
    )
