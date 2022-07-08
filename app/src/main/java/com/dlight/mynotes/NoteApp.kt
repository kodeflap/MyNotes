package com.dlight.mynotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//root class for injecting dependencies
@HiltAndroidApp
class NoteApp : Application() {
}