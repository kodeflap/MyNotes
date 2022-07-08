package com.dlight.mynotes.feature_note.domain.use_cases

import com.dlight.mynotes.feature_note.domain.model.Note
import com.dlight.mynotes.feature_note.domain.repository.NoteRepository

//class to get notes by IDs
class GetNote(private val repository: NoteRepository) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}