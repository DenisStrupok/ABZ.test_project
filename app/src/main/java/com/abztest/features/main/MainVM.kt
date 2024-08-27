package com.abztest.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainVM : ViewModel() {

    private val _screen = MutableLiveData<String>()
    val screen: LiveData<String> = _screen
    fun setType(selectedScreen: String) {
        _screen.value = selectedScreen
    }


}