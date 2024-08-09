package com.example.prueba.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.entity.RecipeDao
import com.example.prueba.entity.RecipeEntity
import com.example.prueba.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class RecipeRoomViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    private val _recipesFromDb = MutableLiveData<List<RecipeEntity>>()
    val recipesFromDb: LiveData<List<RecipeEntity>> get() = _recipesFromDb

    init {
        insertDummyData()
        fetchAllRecipes()
    }

    private fun insertDummyData() {
        viewModelScope.launch {
            val response = RetrofitClient.instance.getRecipes()
            if (response.isSuccessful) {
                response.body()?.let {
                    // Inserto datos en la base de datos
                    val recipeEntities = it.data.map { recipe ->
                        RecipeEntity(
                            id = recipe.id.toLong(),
                            title = recipe.title,
                            description = recipe.description,
                            tags = recipe.tags,
                            photoUrl = recipe.photoUrl,
                            ingredients = recipe.ingredients,
                            directions = recipe.directions,
                            totalTime = recipe.totalTime.toLong(),
                            cuisine = recipe.cuisine,
                            rating = recipe.rating.toDoubleOrNull() ?: 0.0
                        )
                    }
                    recipeDao.insertAll(recipeEntities)
//                    _recipes.value = Result.success(it)
                    Log.d(
                        "RecipeRoomViewModel",
                        "Recipes fetched successfully from API $it"
                    )

                } ?: run {
//                    _recipes.value = Result.failure(Exception("Empty response body"))
                }
            } else {
//                _recipes.value = Result.failure(Exception("Error: ${response.errorBody()?.string()}"))
            }

        }
    }

    private fun fetchAllRecipes() {
        viewModelScope.launch {
            _recipesFromDb.value = recipeDao.getAllRecipes()
        }
    }

    fun getRecipeById(id: Long): LiveData<RecipeEntity> {
        val recipeLiveData = MutableLiveData<RecipeEntity>()
        viewModelScope.launch {
            recipeLiveData.value = recipeDao.getRecipeById(id)
        }
        return recipeLiveData
    }

    fun searchRecipes(query: String): LiveData<List<RecipeEntity>> {
        val searchResults = MutableLiveData<List<RecipeEntity>>()
        viewModelScope.launch {
            searchResults.value = recipeDao.searchRecipes(query)
        }
        return searchResults
    }
}
