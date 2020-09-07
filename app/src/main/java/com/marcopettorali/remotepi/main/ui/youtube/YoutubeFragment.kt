package com.marcopettorali.remotepi.main.ui.youtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.YoutubeFragmentBinding
import com.marcopettorali.remotepi.hideKeyboard
import com.marcopettorali.remotepi.network.NetworkUtils
import com.marcopettorali.remotepi.network.YOUTUBE_REQUEST_MSG

class YoutubeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: YoutubeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.youtube_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = YoutubeViewModelFactory(application)
        val youtubeViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(YoutubeViewModel::class.java)

        binding.viewModel = youtubeViewModel

        youtubeViewModel.goButtonClicked.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                view?.let { hideKeyboard(it) }
                NetworkUtils.send(YOUTUBE_REQUEST_MSG + binding.youtubeSearchTextView.text)
                youtubeViewModel.go(binding.youtubeSearchTextView.text.toString())
                youtubeViewModel.onGoButtonClickedCompleted()
                binding.youtubeSearchTextView.setText("")
            }
        })

        binding.youtubeSearchTextView.setOnEditorActionListener(OnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                view?.let { hideKeyboard(it) }
                NetworkUtils.send(YOUTUBE_REQUEST_MSG + binding.youtubeSearchTextView.text)
                youtubeViewModel.go(binding.youtubeSearchTextView.text.toString())
                binding.youtubeSearchTextView.setText("")
                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

}