package com.abztest.features.connection

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentConnectionErrorBinding
import com.abztest.helper.NetworkConnection
import org.koin.android.ext.android.inject

class ConnectionErrorFragment : Fragment(R.layout.fragment_connection_error) {

    private val binding: FragmentConnectionErrorBinding by viewBinding()
    private val networkConnection: NetworkConnection by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        connectionErroTryAgainBtn.setOnClickListener {
            if (networkConnection.isConnected()) {
                findNavController().popBackStack()
            }
        }
    }
}