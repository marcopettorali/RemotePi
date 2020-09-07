package com.marcopettorali.remotepi.main.ui.main_menu

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainViewModel(private val application:Application ): ViewModel() {

    private val _navigateToActionFragment = MutableLiveData<Long>()
    val navigateToActionFragment
        get() = _navigateToActionFragment


    fun onButtonClicked(id: Long) {
        _navigateToActionFragment.value = id
        Timber.i( "Button ${id} Clicked! Action = ${BUTTONS_LIST[id.toInt()].name}")
    }

    fun onNavigationCompleted(){
        _navigateToActionFragment.value = null
    }

}