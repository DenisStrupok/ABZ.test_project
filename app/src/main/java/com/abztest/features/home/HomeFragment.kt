package com.abztest.features.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        const val SELECTED_TYPE_USERS = "selected type users"
        const val SELECTED_TYPE_ADD_USER = "selected type add user"
    }

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeVM by viewModel()
    private lateinit var usersAdapter: UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun initViews() = with(binding) {
        binding.homeListRV.adapter = usersAdapter
        homeAddUserContainer.setOnClickListener {
            viewModel.setSelectedItem(SELECTED_TYPE_ADD_USER)
        }

        homeUsersContainer.setOnClickListener {
            viewModel.setSelectedItem(SELECTED_TYPE_USERS)
        }

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


        selectedItem.observe(viewLifecycleOwner) { type ->
            when (type) {
                SELECTED_TYPE_ADD_USER -> {
                    binding.homeAddUserContainer.isSelected = true
                    binding.homeUsersContainer.isSelected = false
                    findNavController().navigate(R.id.actionHomeFragmentToAddUserFragment)
                }

                SELECTED_TYPE_USERS -> {
                    binding.homeAddUserContainer.isSelected = false
                    binding.homeUsersContainer.isSelected = true
                }
            }
        }
    }
}