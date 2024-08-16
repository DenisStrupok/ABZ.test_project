package com.abztest.features.add.failed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentFailedBinding

class FailedFragment : Fragment(R.layout.fragment_failed) {

    private val binding: FragmentFailedBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        failedTryBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}