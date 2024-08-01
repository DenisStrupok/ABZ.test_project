package com.abztest.features.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abztest.domain.usecases.TestUseCase
import kotlinx.coroutines.launch

class SplashVM(
    private val testUseCase: TestUseCase
) : ViewModel() {

    val test = MutableLiveData(false)

    init {
        testFun()
    }

    private fun testFun() {
        viewModelScope.launch {
            test.value = testUseCase.invoke(TestUseCase.Params())
        }
    }
}