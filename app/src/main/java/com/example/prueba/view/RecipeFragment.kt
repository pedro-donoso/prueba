package com.example.prueba.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.models.RecipeApp
import com.example.prueba.viewModels.RecipeRoomViewModel
import com.example.prueba.viewModels.RecipeRoomViewModelFactory
import com.example.prueba.R
import com.example.prueba.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeRoomViewModel by viewModels {
        RecipeRoomViewModelFactory((requireActivity().application as
                RecipeApp).database.recipeDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recipeRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)

        recipeViewModel.recipesFromDb.observe(viewLifecycleOwner, Observer
        { recipes ->
            recipes?.let {
                recyclerView.adapter = RecipeAdapter(it) { recipeId ->
                    val bundle = Bundle().apply {
                        putLong("recipeId", recipeId)
                    }
                    findNavController().navigate(R.id.action_recipeFragment_to_recipeDetailFragment, bundle)
                }
                Log.d("RecipeFragment", "Number of recipes: ${it.size}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
