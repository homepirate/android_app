package com.example.lab1

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
                characterDao.insertCharacter(characterEntity)
            }
        }
    }


    fun getAllCharactersFlow(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
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

    fun clearTable() {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.clearTable()
        }
    }
}
