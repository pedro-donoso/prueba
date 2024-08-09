package com.example.prueba.entity

//IMPORTACIONES
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//INTERFAZ DE ACCESO A DATOS PARA ROOM
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :searchQuery || '%' OR cuisine LIKE '%' || :searchQuery || '%' OR tags LIKE '%' || :searchQuery || '%'")
    suspend fun searchRecipes(searchQuery: String): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Long): RecipeEntity

}