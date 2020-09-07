package com.marcopettorali.remotepi.mouse

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.MouseFragmentBinding


class MouseFragment : Fragment(), SensorEventListener {

    lateinit var mouseViewModel: MouseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MouseFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.mouse_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MouseViewModelFactory(application)
        mouseViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MouseViewModel::class.java)

        binding.viewModel = mouseViewModel
        mouseViewModel.initializeSensors(this)


        binding.leftButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onLeftButtonClicked(
                view,
                motionEvent
            )
        }
        binding.rightButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onRightButtonClicked(
                view,
                motionEvent
            )
        }
        binding.leftCornerButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onBottomLeftCornerButtonClicked(
                view,
                motionEvent
            )
        }
        binding.rightCornerButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onTopRightCornerButtonClicked(
                view,
                motionEvent
            )
        }
        binding.pageUpButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onPageUpButtonClicked(
                view,
                motionEvent
            )
        }
        binding.pageDownButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onPageDownButtonClicked(
                view,
                motionEvent
            )
        }
        binding.sendTextButton.setOnTouchListener { view, motionEvent ->
            mouseViewModel.onSendTextButtonClicked(
                view,
                motionEvent,
                binding.sendTextEditText.text.toString()
            )
        }

        return binding.root
    }

//    fun onKeyDown(keyCode: Int, event: KeyEvent?) {
//        event?.let { mouseViewModel.onKeyDown(keyCode, it) }
//    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { mouseViewModel.onSensorChanged(it) }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}