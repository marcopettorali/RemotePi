package com.marcopettorali.remotepi.mouse

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModel
import com.marcopettorali.remotepi.network.*
import timber.log.Timber

class MouseViewModel(private val application: Application) : ViewModel() {

    private var yValue: Double = 0.0
    private var zValue: Double = 0.0

    private var bottomLeftCornerSet = false
    private var topRightCornerSet = false

    fun initializeSensors(fragment: MouseFragment) {
        //initialize sensors
        val sensorManager =
            fragment.activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
            fragment,
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
            SensorManager.SENSOR_DELAY_FASTEST
        )
        sensorManager.registerListener(
            fragment,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    fun onSensorChanged(event: SensorEvent) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            yValue = event!!.values[1].toDouble()
        }
        if (event!!.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            zValue = event!!.values[2].toDouble()
        }
        Timber.i(((Math.round(100.0 * yValue) / 100.0).toString() + ";" + Math.round(100.0 * zValue) / 100.0))
        if (bottomLeftCornerSet && topRightCornerSet) {
            val msg = MOUSE_POSITION_HEADER + "Y" + yValue + "C" + zValue
            NetworkUtils.send(msg)
        }
    }

    fun onBottomLeftCornerButtonClicked(view: View, event: MotionEvent): Boolean {
        val msg = MOUSE_SCREEN_REF_HEADER + "BY" + yValue + "C" + zValue
        bottomLeftCornerSet = true
        NetworkUtils.send(msg)
        return true
    }

    fun onTopRightCornerButtonClicked(view: View, event: MotionEvent): Boolean {
        val msg = MOUSE_SCREEN_REF_HEADER + "TY" + yValue + "C" + zValue
        topRightCornerSet = true
        NetworkUtils.send(msg)
        return true
    }

    fun onLeftButtonClicked(view: View, event: MotionEvent): Boolean {
        var msg = ""
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            msg = MOUSE_BUTTON_LEFT_DOWN_MSG
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            msg = MOUSE_BUTTON_LEFT_UP_MSG
        }
        NetworkUtils.send(msg)
        return true
    }

    fun onRightButtonClicked(view: View, event: MotionEvent): Boolean {
        var msg = ""
        if (event.action == MotionEvent.ACTION_DOWN) {
            msg = MOUSE_BUTTON_RIGHT_DOWN_MSG
        } else if (event.action == MotionEvent.ACTION_UP) {
            msg = MOUSE_BUTTON_RIGHT_UP_MSG
        }
        NetworkUtils.send(msg)
        return true
    }

    fun onPageUpButtonClicked(view: View, event: MotionEvent): Boolean {
        val msg = MOUSE_WHEEL_UP_MSG
        NetworkUtils.send(msg)
        return true
    }

    fun onPageDownButtonClicked(view: View, event: MotionEvent): Boolean {
        val msg = MOUSE_WHEEL_DOWN_MSG
        NetworkUtils.send(msg)
        return true
    }

    fun onSendTextButtonClicked(view: View, event: MotionEvent, text: String): Boolean {
        NetworkUtils.send(TEXT_HEADER + text)
        return true
    }

}