package com.abztest.features.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abztest.domain.models.UserModel
import com.abztest.domain.usecases.user.GetListUsersUseCase
import kotlinx.coroutines.launch

class UsersVM(
    private val getListUsersUseCase: GetListUsersUseCase
) : ViewModel() {
    companion object {
        const val PAGE_COUNT = 6
    }

    private val _listUsers = MutableLiveData<MutableList<UserModel>>()
    val listUsers: LiveData<MutableList<UserModel>> = _listUsers

    private val _selectedItem = MutableLiveData<String>()
    val selectedItem: LiveData<String> = _selectedItem

    private val _isShowProgressLoader = MutableLiveData(false)
    val isShowProgressLoader: LiveData<Boolean> = _isShowProgressLoader

    private val _userRegisterId = MutableLiveData<String>()


    private var currentPage = 1

    fun getUsers() {
        viewModelScope.launch {
            val result = getListUsersUseCase.invoke(
                GetListUsersUseCase.Params(
                    page = currentPage,
                    count = PAGE_COUNT
                )
            )
            result?.users?.let { _listUsers.value = it }
            val newUser = listUsers.value?.find { it.id == _userRegisterId.value?.toInt() }
            val list: MutableList<UserModel>? =
                _listUsers.value?.filterNot { it == newUser }?.toMutableList()
            newUser?.let { list?.add(0, it) }
            list?.let { _listUsers.value = it }
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
            _listUsers.value = result?.users ?: mutableListOf()
        }
    }

    fun setProgressLoader(isShow: Boolean) {
        _isShowProgressLoader.value = isShow
    }

    fun setRegisterUserId(id: String) {
        _userRegisterId.value = id
    }

}