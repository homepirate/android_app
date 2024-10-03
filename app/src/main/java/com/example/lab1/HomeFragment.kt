package com.example.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter
    private val characters: MutableList<Character> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false
    private var isSearching = false
    private var searchQuery: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = gridLayoutManager
        characterAdapter = CharacterAdapter(characters)
        binding.recyclerView.adapter = characterAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                        if (isSearching && !searchQuery.isNullOrEmpty()) {
                            searchCharactersByName(searchQuery!!, currentPage)
                        } else {
                            fetchCharacters(currentPage)
                        }
                    }
                }
            }
        })


        binding.searchButton.setOnClickListener {
            val name = binding.searchInput.text.toString()
            currentPage = 1
            characters.clear()
            characterAdapter.notifyDataSetChanged()

            if (name.isNotBlank()) {
                isSearching = true
                searchQuery = name
                searchCharactersByName(name, currentPage)
            } else {
                isSearching = false
                fetchCharacters(currentPage)
            }
        }

        fetchCharacters(currentPage)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCharacters(page: Int) {
        isLoading = true
        ApiClient.apiService.getCharacters(page).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                isLoading = false
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.results?.let {
                        characters.addAll(it)
                        characterAdapter.notifyDataSetChanged()
                    }
                    currentPage++
                }
                println(page)
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                isLoading = false
            }
        })
    }

    private fun searchCharactersByName(name: String, page: Int) {
        isLoading = true
        println(name + page)
        ApiClient.apiService.getCharactersByName(name.trim(), page).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                isLoading = false
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.results?.let {
                        characters.addAll(it)
                        characterAdapter.notifyDataSetChanged()
                    }
                    currentPage++
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                isLoading = false
            }
        })
    }
}
