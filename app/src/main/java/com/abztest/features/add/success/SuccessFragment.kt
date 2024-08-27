package com.abztest.features.add.success

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentSuccessBinding
import com.abztest.helper.USER_ID
import com.abztest.helper.USER_SHARED_PREF

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding: FragmentSuccessBinding by viewBinding()
    private lateinit var sharedPreferences: SharedPreferences
    private val args: SuccessFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedPreferences =
            requireActivity().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        activity?.findViewById<LinearLayout>(R.id.mainAddUserContainer)?.visibility = View.GONE
        activity?.findViewById<LinearLayout>(R.id.mainUsersContainer)?.visibility = View.GONE

        initViews()
    }

    private fun initViews() = with(binding) {
        successGotItBtn.setOnClickListener {
            saveUserId()
            findNavController().popBackStack(R.id.usersFragment, true)
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveUserId() {
        val editor = sharedPreferences.edit()
        editor.putString(USER_ID, args.userId)
        editor.apply()
    }
}