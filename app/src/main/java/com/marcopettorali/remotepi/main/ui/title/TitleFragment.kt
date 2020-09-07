package com.marcopettorali.remotepi.main.ui.title

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.TitleFragmentBinding
import com.marcopettorali.remotepi.hideBottomNavigationBar
import com.marcopettorali.remotepi.hideKeyboard
import com.marcopettorali.remotepi.network.HIDE_QR_MSG
import com.marcopettorali.remotepi.network.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = TitleViewModelFactory(application)
        val titleViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TitleViewModel::class.java)

        binding.viewModel = titleViewModel

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.let { hideBottomNavigationBar(it) };

        var qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator?.setOrientationLocked(false)
        qrScanIntegrator?.setBeepEnabled(false)
        qrScanIntegrator?.setPrompt(getString(R.string.scan_prompt_message))


        titleViewModel.goButtonClicked.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                view?.let { hideKeyboard(it) }
                qrScanIntegrator.initiateScan()
                titleViewModel.onGoButtonClickedCompleted()
            }
        })

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            var str = result.contents
            //setting values to textviews
            var address = str.split(":".toRegex()).toTypedArray()[0]
            var port = str.split(":".toRegex()).toTypedArray()[1]
            NetworkUtils.setAddressAndPort(address, port.toInt())

            val uiScope = CoroutineScope(Dispatchers.IO)
            uiScope.launch {
                if (!NetworkUtils.checkConnectionWithRemoteServer()) {
                    throw Exception()
                }
            }
            
            this.findNavController()
                .navigate(TitleFragmentDirections.actionTitleFragmentToMainFragment())
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                String.format(getString(R.string.on_correct_connection_with_server_snackbar), str),
                Snackbar.LENGTH_LONG
            ).show()
            NetworkUtils.send(HIDE_QR_MSG)
        } catch (e: Exception) {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                getString(R.string.on_failed_connection_with_server_snackbar),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
