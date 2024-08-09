package com.example.prueba.models

data class RecipesResponse(
    val data: List<Datum>
)

data class Datum (
    val id: Long,
    val title: String,
    val course: Course,
    val cuisine: String,
    val mainIngredient: String,
    val description: String,
    val source: String,
    val url: String,
    val urlHost: String,
    val prepTime: Long,
    val cookTime: Long,
    val totalTime: Long,
    val servings: Long,
    val yield: String,
    val ingredients: String,
    val directions: String,
    val tags: String,
    val rating: String,
    val publicURL: String,
    val photoUrl: String,
    val private: Private,
    val nutritionalScoreGeneric: String,
    val calories: Number,
    val fat: String,
    val cholesterol: String,
    val sodium: String,
    val sugar: String,
    val carbohydrate: String,
    val fiber: String,
    val protein: String,
    val cost: String
)

enum class Course {
    Breakfast,
    MainCourse,
    SnacksAndSandwiches
}

enum class Private {
    No
}