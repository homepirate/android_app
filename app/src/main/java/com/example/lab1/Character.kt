package com.example.lab1
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)

@Serializable
data class CharacterResponse(
    val results: List<Character>
)