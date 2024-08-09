package com.example.prueba.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.R
import com.example.prueba.databinding.FragmentRecipeBinding
import com.example.prueba.models.RecipeApp
import com.example.prueba.viewModels.RecipeRoomViewModel
import com.example.prueba.viewModels.RecipeRoomViewModelFactory

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
        val adapter = RecipeAdapter(listOf()) { recipeId ->
            val bundle = Bundle().apply {
                putLong("recipeId", recipeId)
            }
            findNavController().navigate(R.id.action_recipeFragment_to_recipeDetailFragment, bundle)
        }
        recyclerView.adapter = adapter

        recipeViewModel.recipesFromDb.observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                adapter.updateRecipes(it)
                Log.d("RecipeFragment", "Number of recipes: ${it.size}")
            }
        })
        setHasOptionsMenu(true)
    }

    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val adapter = binding.recipeRecycler.adapter as RecipeAdapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.length >= 4) {
                        searchRecipes(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.length >= 4) {
                        searchRecipes(it)
                    } else {
                        // Mostrar todas las recetas si la longitud es menor que 4
                        recipeViewModel.recipesFromDb.observe(viewLifecycleOwner, Observer { recipes ->
                            recipes?.let { adapter.updateRecipes(it) }
                        })
                    }
                }
                return false
            }
        })
    }

    private fun searchRecipes(query: String) {
        recipeViewModel.searchRecipes(query).observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                val adapter = binding.recipeRecycler.adapter as RecipeAdapter
                adapter.updateRecipes(it)
                Log.d("RecipeFragment", "Number of search results: ${it.size}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
















