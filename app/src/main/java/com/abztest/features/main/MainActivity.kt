package com.abztest.features.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.abztest.R
import com.abztest.databinding.ActivityMainBinding
import com.abztest.helper.NetworkConnection
import com.abztest.helper.SELECTED_SCREEN_ADD_USER
import com.abztest.helper.SELECTED_SCREEN_USERS
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val networkConnection: NetworkConnection by inject()
    private val viewModel: MainVM by viewModel()
    private val binding: ActivityMainBinding by viewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK
        viewModel.setType(SELECTED_SCREEN_USERS)

        initViews()
        initObservers()

        networkConnection.observe(this) { isConnection ->
            if (!isConnection) {
                navController.navigate(R.id.actionGlobalConnectionErrorFragment)
            }
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }

        })

    }

    private fun initViews() = with(binding) {
        mainAddUserContainer.setOnClickListener {
            viewModel.setType(SELECTED_SCREEN_ADD_USER)
        }
        mainUsersContainer.setOnClickListener {
            viewModel.setType(SELECTED_SCREEN_USERS)
        }
        navController.addOnDestinationChangedListener { controller, _, _ ->
            when (controller.currentDestination?.id) {
                R.id.usersFragment -> {
                    binding.mainAddUserContainer.isSelected = false
                    binding.mainUsersContainer.isSelected = true
                }

                R.id.addUserFragment -> {
                    binding.mainAddUserContainer.isSelected = true
                    binding.mainUsersContainer.isSelected = false
                }
            }
        }
    }

    private fun initObservers() = with(viewModel) {
        screen.observe(this@MainActivity) { screen ->
            when (screen) {
                SELECTED_SCREEN_USERS -> {
                    if (navController.currentDestination?.id != R.id.usersFragment) {
                        navController.popBackStack(R.id.usersFragment, true)
                    }
                }

                SELECTED_SCREEN_ADD_USER -> {
                    if (navController.currentDestination?.id != R.id.addUserFragment) {
                        navController.navigate(R.id.actionHomeFragmentToAddUserFragment)
                    }
                }
            }
        }
    }
}