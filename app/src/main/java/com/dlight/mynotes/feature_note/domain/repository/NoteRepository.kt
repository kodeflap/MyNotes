package com.dlight.mynotes.feature_note.domain.repository

import com.dlight.mynotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

//fake version of our repository which stimulates same behaviour of our repository to pass to uses cases

interface NoteRepository {

    //function to get notes from Note database
    fun getNotes(): Flow<List<Note>>

    //function to getNote by IDs
    suspend fun getNoteById(id: Int): Note?

    //function to insert notes
    suspend fun insertNote(note: Note)

    //function to delete note
    suspend fun deleteNote(note: Note)
}