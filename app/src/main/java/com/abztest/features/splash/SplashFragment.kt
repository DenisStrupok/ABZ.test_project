package com.abztest.features.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abztest.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashVM by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.test.observe(viewLifecycleOwner) { test ->
            Toast.makeText(requireContext(), "Check -> $test", Toast.LENGTH_SHORT).show()
        }
    }
}