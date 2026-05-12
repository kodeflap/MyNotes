package com.dlight.mynotes.feature_note.presentation.notes.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dlight.mynotes.core.util.TestTags
import com.dlight.mynotes.feature_note.presentation.notes.NoteViewModel
import com.dlight.mynotes.feature_note.presentation.notes.NotesEvent
import com.dlight.mynotes.feature_note.presentation.util.Screen
import com.nesyou.staggeredgrid.LazyStaggeredGrid
import com.nesyou.staggeredgrid.StaggeredCells
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },

                containerColor = MaterialTheme.colorScheme.primary

            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "My Notes",
                    style = MaterialTheme.typography.headlineSmall
                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,

                enter = fadeIn() + slideInVertically(),

                exit = fadeOut() + slideOutVertically()
            ) {

                OrderSection(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(TestTags.ORDER_SECTION),

                    noteOrder = state.noteOrder,

                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyStaggeredGrid(

                cells = StaggeredCells.Fixed(2),

                contentPadding = PaddingValues(horizontal = 8.dp)

            ) {

                items(state.notes.size) { index ->

                    val note = state.notes[index]

                    val randomHeight =
                        (100..350).random().dp

                    NoteItem(

                        note = note,

                        modifier = Modifier
                            .height(randomHeight)
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {

                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },

                        onDeleteClick = {

                            viewModel.onEvent(
                                NotesEvent.DeleteNote(note)
                            )

                            scope.launch {

                                val result =
                                    snackbarHostState.showSnackbar(

                                        message = "Note deleted",

                                        actionLabel = "Undo"
                                    )

                                if (result == SnackbarResult.ActionPerformed) {

                                    viewModel.onEvent(
                                        NotesEvent.RestoreNote
                                    )
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}