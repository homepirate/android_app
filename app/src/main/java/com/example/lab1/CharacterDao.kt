package com.example.lab1
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Delete
    fun deleteCharacter(character: CharacterEntity)

    @Query("DELETE FROM characters")
    fun clearTable()
}

