package com.dlight.mynotes.feature_note.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dlight.mynotes.core.util.TestTags
import com.dlight.mynotes.di.AppModule
import com.dlight.mynotes.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.dlight.mynotes.feature_note.presentation.notes.components.NotesScreen
import com.dlight.mynotes.feature_note.presentation.util.Screen
import com.dlight.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            MyNotesTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test

    fun saveNewNote_editAfterwards() {

        //Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add note").performClick()

        //Enter text in title and context text field
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test_title")

        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test_content")

        //save the node
        composeRule.onNodeWithContentDescription("Save").performClick()


        //Make sure there is no note in the list with this title and content
        composeRule.onNodeWithText("test_title").assertIsDisplayed()

        //Click on note to edit it
        composeRule.onNodeWithText("test_content").performClick()

        //Make sure title and context text field contains note title and content
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test_title")

        composeRule.onNodeWithText(TestTags.CONTENT_TEXT_FIELD).assertTextEquals("test_content")

        //Add text "2" to the title text field
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("2")

        //Update the note
        composeRule.onNodeWithContentDescription("Save").performClick()

        //make sure the update was applied to the list
        composeRule.onNodeWithText("test_title2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {

            //Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add note").performClick()

            //Enter text in title and context text field
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())

            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())

            //save the node
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort").performClick()

        composeRule.onNodeWithContentDescription("Title").performClick()

        composeRule.onNodeWithContentDescription("Description").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("3")

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("2")

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("1")

    }
}