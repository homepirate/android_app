package com.example.lab1

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()


    private lateinit var characterAdapter: CharacterAdapter
    private val characters: MutableList<Character> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false
    private var isSearching = false
    private var searchQuery: String? = null
    private val characterRepository = CharacterRepository(ApiClient.apiService)


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

        binding.settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }


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

        saveCharactersToFile()


        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCharacters(page: Int) {
        isLoading = true
        characterRepository.getCharacters(page) { result, error ->
            isLoading = false
            if (error != null) {
                println("Error fetching characters: ${error.message}")
            } else {
                result?.let {
                    characters.addAll(it)
                    characterAdapter.notifyDataSetChanged()
                    currentPage++
                }
            }
        }
    }

    private fun searchCharactersByName(name: String, page: Int) {
        isLoading = true
        characterRepository.searchCharactersByName(name, page) { result, error ->
            isLoading = false
            if (error != null) {
                println("Error searching characters: ${error.message}")
            } else {
                result?.let {
                    characters.addAll(it)
                    characterAdapter.notifyDataSetChanged()
                    currentPage++
                }
            }
        }
    }

    private fun saveCharactersToFile() {
        isLoading = true
        characterRepository.getCharacters(1) { result, error ->
            isLoading = false
            if (error != null) {
                println("Error fetching characters: ${error.message}")
            } else {
                result?.let { characters ->
                    val fileName = "loginov.txt"
                    val content = characters.joinToString("\n") { character -> character.toString() }

                    println("Save to file")

                    val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

                    val file = File(documentsDir, fileName)

                    if (file.exists()) {
                        println("File already exists at: ${file.absolutePath}. No new file created.")
                        return@let
                    }

                    try {
                        file.writeText(content)
                        println("File saved at: ${file.absolutePath}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        println("Error saving file: ${e.message}")
                    }
                }
            }
        }
    }

}
