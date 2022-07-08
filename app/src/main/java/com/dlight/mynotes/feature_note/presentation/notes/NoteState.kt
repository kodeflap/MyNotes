package com.dlight.mynotes.feature_note.presentation.notes

import com.dlight.mynotes.feature_note.domain.model.Note
import com.dlight.mynotes.feature_note.domain.util.NoteOrder
import com.dlight.mynotes.feature_note.domain.util.OrderType

//data class to track the state of app

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
