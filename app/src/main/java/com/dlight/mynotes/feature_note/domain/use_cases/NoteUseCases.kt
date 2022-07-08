package com.dlight.mynotes.feature_note.domain.use_cases

//data class for note feature use cases for view model

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)
