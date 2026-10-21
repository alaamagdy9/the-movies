package com.nweave.movies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt builds its container here, once, before any screen opens.
@HiltAndroidApp
class MoviesApplication : Application()