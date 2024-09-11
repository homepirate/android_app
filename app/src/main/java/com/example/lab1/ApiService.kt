package com.example.lab1
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("character")
    fun getCharacters(@retrofit2.http.Query("page") page: Int): Call<CharacterResponse>

    @GET("character")
    fun getCharactersByName(@retrofit2.http.Query("name",) name: String, @retrofit2.http.Query("page",) page: Int): Call<CharacterResponse>
}
