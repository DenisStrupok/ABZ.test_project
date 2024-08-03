package com.abztest.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abztest.domain.usecases.token.GetAccessTokenUseCase
import kotlinx.coroutines.launch

class SplashVM(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    val check = MutableLiveData("")

    init {
        viewModelScope.launch {
            check.value = getAccessTokenUseCase.invoke(Unit)
        }
    }
}