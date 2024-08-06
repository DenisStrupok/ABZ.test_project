package com.abztest.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abztest.domain.models.UserModel
import com.abztest.domain.usecases.user.GetListUsersUseCase
import com.abztest.features.home.HomeFragment.Companion.SELECTED_TYPE_USERS
import kotlinx.coroutines.launch

class HomeVM(
    private val getListUsersUseCase: GetListUsersUseCase
) : ViewModel() {
    companion object {
        const val PAGE_COUNT = 6
    }

    private val _listUsers = MutableLiveData<List<UserModel>>()
    val listUsers: LiveData<List<UserModel>> = _listUsers

    private val _selectedItem = MutableLiveData<String>()
    val selectedItem: LiveData<String> = _selectedItem

    private val _isShowProgressLoader = MutableLiveData(false)
    val isShowProgressLoader: LiveData<Boolean> = _isShowProgressLoader

    private var currentPage = 1

    fun setSelectedItem(type: String) {
        _selectedItem.value = type
        actionType()
    }

    private fun actionType() {
        when (selectedItem.value) {
            SELECTED_TYPE_USERS -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            val result = getListUsersUseCase.invoke(
                GetListUsersUseCase.Params(
                    page = currentPage,
                    count = PAGE_COUNT
                )
            )
            currentPage = result?.page ?: 0
            _listUsers.value = result?.users?.sortedBy { it.registrationTime }
        }
    }

    fun loadMoreUsers() {
        currentPage++
        viewModelScope.launch {
            val result = getListUsersUseCase.invoke(
                GetListUsersUseCase.Params(
                    page = currentPage,
                    count = PAGE_COUNT
                )
            )
            _isShowProgressLoader.value = false
            currentPage = result?.page ?: 1
            _listUsers.value = result?.users?.sortedBy { it.registrationTime }
            listUsers.value.toString()
            val s = 0
        }
    }

    fun setProgressLoader(isShow: Boolean) {
        _isShowProgressLoader.value = isShow
    }

}