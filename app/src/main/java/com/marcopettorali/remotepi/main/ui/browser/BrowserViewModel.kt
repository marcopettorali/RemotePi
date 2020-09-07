package com.marcopettorali.remotepi.main.ui.browser

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BrowserViewModel(private val application: Application) : ViewModel() {

    private var _goButtonClicked = MutableLiveData<Boolean>()
    val goButtonClicked
        get() = _goButtonClicked

    fun onGoButtonClicked(){
        _goButtonClicked.value = true
    }

    fun onGoButtonClickedCompleted(){
        _goButtonClicked.value = false
    }

    fun go(search: String){
        //send search to server

    }


}