package com.marcopettorali.remotepi.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.KeyboardFragmentBinding


class KeyboardFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: KeyboardFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.keyboard_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = KeyboardViewModelFactory(application)
        val keyboardViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(KeyboardViewModel::class.java)

        binding.viewModel = keyboardViewModel

        binding.sendTextButton.setOnTouchListener { view, motionEvent ->

            keyboardViewModel.onSendTextButtonClicked(
                view,
                motionEvent,
                binding.sendTextEditText.text.toString()

            )
            //binding.sendTextEditText.setText("")
        }

        return binding.root
    }
}