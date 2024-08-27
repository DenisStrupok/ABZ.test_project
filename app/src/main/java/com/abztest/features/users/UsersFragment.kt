package com.abztest.features.users

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentUsersBinding
import com.abztest.helper.USER_ID
import com.abztest.helper.USER_SHARED_PREF
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val binding: FragmentUsersBinding by viewBinding()
    private val viewModel: UsersVM by viewModel()
    private lateinit var sharedPref: SharedPreferences
    private lateinit var usersAdapter: UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUsers()

        activity?.findViewById<LinearLayout>(R.id.mainAddUserContainer)?.visibility = View.VISIBLE
        activity?.findViewById<LinearLayout>(R.id.mainUsersContainer)?.visibility = View.VISIBLE

        usersAdapter = UsersAdapter(
            isLastItem = { isLast ->
                if (isLast) {
                    viewModel.setProgressLoader(true)
                    viewModel.loadMoreUsers()
                }
            }
        )
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        getRegistrationUserId()
        viewModel.getUsers()
    }

    private fun initViews() = with(binding) {
        binding.homeListRV.adapter = usersAdapter
    }

    private fun initObservers() = with(viewModel) {
        listUsers.observe(viewLifecycleOwner) { users ->
            if (users.isNotEmpty()) {
                binding.homeEmptyState.visibility = View.GONE
                usersAdapter.setListUsers(users)
            } else {
                binding.homeEmptyState.visibility = View.VISIBLE
            }
        }

        isShowProgressLoader.observe(viewLifecycleOwner) { isShow ->
            when (isShow) {
                true -> binding.homeProgressLoader.visibility = View.VISIBLE
                false -> binding.homeProgressLoader.visibility = View.GONE
            }
        }
    }

    private fun getRegistrationUserId() {
        sharedPref =
            requireActivity().getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        sharedPref.getString(USER_ID, "")?.let { id ->
            if (id.isNotEmpty()) {
                viewModel.setRegisterUserId(id)
            }
        }
    }
}