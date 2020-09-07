package com.marcopettorali.remotepi.main.ui.youtube

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class YoutubeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YoutubeViewModel::class.java)) {
            return YoutubeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}