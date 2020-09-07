package com.marcopettorali.remotepi.main.ui.main_menu

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
import androidx.recyclerview.widget.GridLayoutManager
import com.marcopettorali.remotepi.R
import com.marcopettorali.remotepi.databinding.MainFragmentBinding
import com.marcopettorali.remotepi.showBottomNavigationBar


class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)
        val mainViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)


        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = mainViewModel

        val adapter = MainAdapter(ButtonListener(mainViewModel::onButtonClicked))
        binding.buttonList.adapter = adapter

        val manager = GridLayoutManager(activity, 1)
        binding.buttonList.layoutManager = manager

        mainViewModel.navigateToActionFragment.observe(viewLifecycleOwner, Observer { id ->
            id?.let {
                when (BUTTONS_LIST[id.toInt()].name) {
                    "YouTube" -> this.findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToYoutubeFragment())

                    "Browser" -> this.findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToBrowserFragment())

                }
                mainViewModel.onNavigationCompleted()
            }
        })

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.let { showBottomNavigationBar(it) };

        return binding.root
    }

}