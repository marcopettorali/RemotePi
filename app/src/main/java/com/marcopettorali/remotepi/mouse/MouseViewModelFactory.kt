package com.marcopettorali.remotepi.mouse

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MouseViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MouseViewModel::class.java)) {
            return MouseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}