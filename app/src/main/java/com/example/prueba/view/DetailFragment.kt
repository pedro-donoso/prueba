package com.example.prueba.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.prueba.databinding.FragmentDetailBinding
import com.example.prueba.models.RecipeApp
import com.example.prueba.viewModels.RecipeRoomViewModel
import com.example.prueba.viewModels.RecipeRoomViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeRoomViewModel by viewModels {
        RecipeRoomViewModelFactory((requireActivity().application as RecipeApp).database.recipeDao())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = arguments?.getLong("recipeId")
        recipeId?.let {
            recipeViewModel.getRecipeById(it).observe(viewLifecycleOwner, Observer { recipe ->
                recipe?.let { recipeDetail ->
                    binding.recipeTitle.text = recipeDetail.title
                    binding.recipeDescription.text = recipeDetail.description
                    binding.recipeCuisine.text = recipeDetail.cuisine
                    binding.recipeIngredients.text = recipeDetail.ingredients
                    binding.recipeDirections.text = recipeDetail.directions
                    Glide.with(this).load(recipeDetail.photoUrl).into(binding.recipeImage)
                }
            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}