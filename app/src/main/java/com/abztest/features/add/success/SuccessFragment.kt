package com.abztest.features.add.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding: FragmentSuccessBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        successGotItBtn.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, true)
        }
    }
}