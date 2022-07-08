package com.dlight.mynotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dlight.mynotes.feature_note.domain.model.Note

//class with extends from RoomDatabase
@Database(
    entities = [Note::class],
    version = 2
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = " notes_db"
    }

}