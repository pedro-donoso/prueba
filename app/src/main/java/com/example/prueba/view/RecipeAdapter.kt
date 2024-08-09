package com.example.prueba.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.prueba.R
import com.example.prueba.entity.RecipeEntity
import com.google.android.material.textview.MaterialTextView

class RecipeAdapter(
    private var recipes:List<RecipeEntity>,
    private val onClick: (Long) -> Unit
): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    inner class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleView: MaterialTextView = view.findViewById(R.id.recipe_title)
        val descriptionView: MaterialTextView = view.findViewById(R.id.recipe_description)
        val cuisineView: MaterialTextView = view.findViewById(R.id.recipe_cuisine)
        val imageView: ImageView = view.findViewById(R.id.recipe_image)

        fun bind(recipe: RecipeEntity) {
            titleView.text = recipe.title
            descriptionView.text = recipe.description
            cuisineView.text = recipe.cuisine

            Glide.with(itemView.context)
                .load(recipe.photoUrl)
                .into(imageView)

            itemView.setOnClickListener {
                onClick(recipe.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }
    fun updateRecipes(newRecipes: List<RecipeEntity>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return recipes.size
    }
}






