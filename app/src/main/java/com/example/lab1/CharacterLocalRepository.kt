package com.example.lab1

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterLocalRepository(private val characterDao: CharacterDao) {

    fun insertCharacter(character: CharacterEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.insertCharacter(character)
        }
    }

    fun insertCharacters(characters: List<Character>) {
        CoroutineScope(Dispatchers.IO).launch {
            for (character in characters) {
                val characterEntity = CharacterEntity(
                    character.id,
                    character.name,
                    character.status,
                    character.species,
                    character.image
                )
                characterDao.insertCharacter(characterEntity)  // Must be suspend
            }
        }
    }

    fun getCharacters(): List<CharacterEntity>? {
        var characterList: List<CharacterEntity>? = null
        val liveData: LiveData<List<CharacterEntity>> = characterDao.getAllCharacters()

        liveData.observeForever { list ->
            characterList = list
        }

        return characterList
    }

    fun updateCharacter(character: CharacterEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.updateCharacter(character)
        }
    }

    fun deleteCharacter(character: CharacterEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.deleteCharacter(character)
        }
    }
}