package com.marcopettorali.remotepi.keyboard

import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModel
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.network.KEY_HEADER
import com.marcopettorali.remotepi.network.KEY_START
import com.marcopettorali.remotepi.network.NetworkUtils
import com.marcopettorali.remotepi.network.TEXT_HEADER

class KeyboardViewModel(private val application: Application) : ViewModel() {

    var ctrlButtonHold = false
    var altButtonHold = false


    fun onClick(view: View) {
        var msg: String = KEY_HEADER
        if (ctrlButtonHold) msg += "CT"
        if (altButtonHold) msg += "AL"

        msg += KEY_START
        msg += when (view.id) {
            R.id.esc_button -> "ES"
            R.id.tab_button -> "TA"
            R.id.backspace_button -> "BA"
            R.id.home_button -> "HO"
            R.id.end_button -> "EN"
            R.id.arrow_up_button -> "AU"
            R.id.arrow_left_button -> "AL"
            R.id.arrow_down_button -> "AD"
            R.id.arrow_right_button -> "AR"
            else -> ""
        }

        when (view.id) {
            R.id.ctrl_button -> {
                if (ctrlButtonHold == true) ctrlButtonHold = false
                return
            }
            R.id.alt_button -> {
                if (altButtonHold == true) altButtonHold = false
                return
            }
        }

        NetworkUtils.send(msg)
    }

    fun onLongClick(view: View) {
        when (view.id) {
            R.id.ctrl_button -> ctrlButtonHold = true
            R.id.alt_button -> altButtonHold = true
            R.id.backspace_button -> {
                var msg = KEY_HEADER + "BA"
                NetworkUtils.send(msg)
            }
        }
        if (view.background is ColorDrawable) {
            view.setBackgroundColor(R.color.buttonHoldColor.toInt())
        }
    }

    fun onSendTextButtonClicked(view: View, event: MotionEvent, text: String): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            NetworkUtils.send(TEXT_HEADER + text)
        }
        return true
    }

}