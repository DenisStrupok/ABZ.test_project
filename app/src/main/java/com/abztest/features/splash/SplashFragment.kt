package com.abztest.features.splash

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abztest.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashVM by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<LinearLayout>(R.id.mainAddUserContainer)?.visibility = View.GONE
        activity?.findViewById<LinearLayout>(R.id.mainUsersContainer)?.visibility = View.GONE

        hideSystemBars()
        isInternetConnection()
        initObserver()
    }

    private fun hideSystemBars() {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun initObserver() = with(viewModel) {
        token.observe(viewLifecycleOwner) { token ->
            if (token.isNotEmpty()) {
                findNavController().navigate(R.id.actionSplashFragmentToHomeFragment)
            }
        }
    }

    private fun isInternetConnection(): Boolean {

        val connectionManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectionManager.activeNetwork ?: return false
        val activeNetwork = connectionManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}