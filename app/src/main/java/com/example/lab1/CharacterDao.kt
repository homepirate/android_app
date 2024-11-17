package com.example.lab1
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDao {
    @Insert
    fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<CharacterEntity>>

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Delete
    fun deleteCharacter(character: CharacterEntity)
}

