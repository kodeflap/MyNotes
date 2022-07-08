package com.dlight.mynotes.feature_note.data.data_source

import androidx.room.*
import com.dlight.mynotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

//note Dao class for RoomDatabase
@Dao
interface NoteDao {
    // function to get all note as list from note database
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    //function to load notes using IDs
    @Query("SELECT * FROM note WHERE id = :id" )
    suspend fun getNoteById(id: Int): Note?

    //function to insert notes to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    //function to delete notes
    @Delete
    suspend fun deleteNote(note: Note)
}