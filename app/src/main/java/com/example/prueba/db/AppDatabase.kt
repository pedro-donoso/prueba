package com.example.prueba.db

//IMPORTACIONES
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prueba.entity.RecipeDao
import com.example.prueba.entity.RecipeEntity

//BD DE ROOM UTILIZO LA VERSION 4 AUTOMIGRATION
@Database(entities = [RecipeEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        //DEVUELVE LA INSTANCIA UNICA BD
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                )

                    //DESTRUCCION BD EN CASO DE MIGRACION
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
