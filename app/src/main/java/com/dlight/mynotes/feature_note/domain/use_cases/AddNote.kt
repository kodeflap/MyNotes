package com.dlight.mynotes.feature_note.domain.use_cases

import com.dlight.mynotes.feature_note.domain.model.InvalidNoteException
import com.dlight.mynotes.feature_note.domain.model.Note
import com.dlight.mynotes.feature_note.domain.repository.NoteRepository

//class to add note
class AddNote(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note) {

        @Throws(InvalidNoteException::class)
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title of the Note cannot be empty!")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content of note cannot be empty!")
        }
            repository.insertNote(note)
    }
}