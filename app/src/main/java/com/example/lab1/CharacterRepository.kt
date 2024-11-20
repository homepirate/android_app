package com.example.lab1

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterRepository(
    private val apiService: ApiService,
    private val characterLocalRepository: CharacterLocalRepository
) {

    fun getCharacters(page: Int, scope: CoroutineScope, onResult: (List<Character>?, Throwable?) -> Unit) {
        apiService.getCharacters(page).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    val characters = response.body()?.results
                    onResult(characters, null)

                    scope.launch {
                        characters?.let { characterLocalRepository.insertCharacters(it) }
                    }
                } else {
                    onResult(null, Exception("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                onResult(null, t)
            }
        })
    }

    fun searchCharactersByName(name: String, page: Int, onResult: (List<Character>?, Throwable?) -> Unit) {
        apiService.getCharactersByName(name.trim(), page).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    onResult(response.body()?.results, null)
                } else {
                    onResult(null, Exception("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                onResult(null, t)
            }
        })
    }
}