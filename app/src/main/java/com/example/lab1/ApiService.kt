package com.example.lab1
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterResponse>
}