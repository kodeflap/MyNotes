package com.dlight.mynotes.feature_note.di

import android.app.Application
import androidx.room.Room
import com.dlight.mynotes.feature_note.data.data_source.NoteDatabase
import com.dlight.mynotes.feature_note.data.repository.NoteRepositoryImpl
import com.dlight.mynotes.feature_note.domain.repository.NoteRepository
import com.dlight.mynotes.feature_note.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Module class to specify app level database, repository, and use cases

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    //function to provide database
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
      return Room.inMemoryDatabaseBuilder(
          app,
          NoteDatabase::class.java,
      ) .build()
    }
    //function to provide repository
    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
    //function to provide use cases to the app
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}