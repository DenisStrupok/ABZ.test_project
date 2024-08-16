package com.abztest.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abztest.domain.usecases.token.GetAccessTokenUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashVM(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    val token = MutableLiveData("")

    init {
        viewModelScope.launch {
            delay(1000)
            token.value = getAccessTokenUseCase.invoke(Unit)
        }
    }
}