package com.marcopettorali.remotepi.keyboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class KeyboardViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyboardViewModel::class.java)) {
            return KeyboardViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}