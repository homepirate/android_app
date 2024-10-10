package com.example.lab1

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val json = Json { ignoreUnknownKeys = true } // Configure JSON settings


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}