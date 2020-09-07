package com.marcopettorali.remotepi.main.ui.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.BrowserFragmentBinding
import com.marcopettorali.remotepi.hideKeyboard
import com.marcopettorali.remotepi.network.BROWSER_REQUEST_MSG
import com.marcopettorali.remotepi.network.NetworkUtils

class BrowserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: BrowserFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.browser_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = BrowserViewModelFactory(application)
        val browserViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(BrowserViewModel::class.java)

        binding.viewModel = browserViewModel

        browserViewModel.goButtonClicked.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                view?.let { hideKeyboard(it) }
                NetworkUtils.send(BROWSER_REQUEST_MSG + binding.browserSearchTextView.text)
                browserViewModel.go(binding.browserSearchGoButton.text.toString())
                browserViewModel.onGoButtonClickedCompleted()
            }
        })

        binding.browserSearchTextView.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                view?.let { hideKeyboard(it) }
                NetworkUtils.send(BROWSER_REQUEST_MSG + binding.browserSearchTextView.text)
                browserViewModel.go(binding.browserSearchGoButton.text.toString())
                binding.browserSearchTextView.setText("")
                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

}