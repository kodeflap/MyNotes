package com.dlight.mynotes.feature_note.presentation.add_edit_note

//data class to check the text field states

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
