package com.dlight.mynotes.feature_note.presentation.notes

import com.dlight.mynotes.feature_note.domain.model.Note
import com.dlight.mynotes.feature_note.domain.util.NoteOrder

//class for checking the events likes sorting, deleting the note option

sealed class NotesEvent {

    data class Order(val noteOrder: NoteOrder) : NotesEvent()

    data class DeleteNote(val note: Note): NotesEvent()

    object RestoreNote: NotesEvent()

    object ToggleOrderSection: NotesEvent()

}
