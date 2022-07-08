package com.dlight.mynotes.feature_note.domain.use_cases

import com.dlight.mynotes.feature_note.domain.model.Note
import com.dlight.mynotes.feature_note.domain.repository.NoteRepository

//uses case class delete note
class DeleteNote(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}