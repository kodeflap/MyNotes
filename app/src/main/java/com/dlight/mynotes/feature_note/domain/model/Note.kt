package com.dlight.mynotes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dlight.mynotes.ui.theme.*

//model class for note
@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(yellow, green, blue, pink, orange, violet)
    }
}

//class to check invalidate exception in notes
class InvalidNoteException(message: String): Exception(message)