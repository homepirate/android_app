package com.example.lab1

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)

data class CharacterResponse(
    val results: List<Character>
)