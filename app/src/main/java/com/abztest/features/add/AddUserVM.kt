package com.abztest.features.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abztest.domain.body.UserBody
import com.abztest.domain.models.PositionModel
import com.abztest.domain.models.UserRegistrationModel
import com.abztest.domain.usecases.user.CreateUserUseCase
import com.abztest.domain.usecases.user.GetUserPositionsUseCase
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class AddUserVM(
    private val getUserPositionsUseCase: GetUserPositionsUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _name = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _phone = MutableLiveData<String>()
    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String> = _photo

    private val _isNameValid = MutableLiveData<Boolean>()
    val isNameValid: LiveData<Boolean> = _isNameValid

    private val _isPhoneValid = MutableLiveData<Boolean>()
    val isPhoneValid: LiveData<Boolean> = _isPhoneValid

    private val _isPhotoValid = MutableLiveData<Boolean>()
    val isPhotoValid: LiveData<Boolean> = _isPhotoValid

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    private val _selectedPosition = MutableLiveData<PositionModel>()
    private val selectedPosition: LiveData<PositionModel> = _selectedPosition

    private val _listUserPosition = MutableLiveData<List<PositionModel>>()
    val listUserPosition: LiveData<List<PositionModel>> = _listUserPosition

    private val _userRegistration = MutableLiveData<UserRegistrationModel>()
    val userRegistration: LiveData<UserRegistrationModel> = _userRegistration

    init {
        _name.value = ""
        _email.value = ""
        _phone.value = ""
        _photo.value = ""
        getUserPositions()
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPhone(number: String) {
        _phone.value = number
    }

    fun setPosition(position: PositionModel) {
        _selectedPosition.value = position
    }

    fun setPhoto(photo: String) {
        _photo.value = photo
    }

    fun checkFields() {
        _isNameValid.value = _name.value?.isBlank() != true
        _isPhoneValid.value = _phone.value?.let { isPhoneNumberValid(it) } == true
        _isEmailValid.value = isValidEmail(_email.value ?: "")
        _isPhotoValid.value = _photo.value?.isBlank() != true
        createUser()
    }

    private fun isPhoneNumberValid(phone: String): Boolean {
        val regex = Regex("^\\+380\\d{9}\$")
        return phone.matches(regex)
    }

    private fun getUserPositions() {
        viewModelScope.launch {
            val positions = getUserPositionsUseCase.invoke(Unit)
            positions.firstOrNull()?.isSelected = true
            positions.forEach { position ->
                if (position.isSelected) {
                    _selectedPosition.value = position
                }
            }
            _listUserPosition.value = positions
        }
    }

    private fun createUser() {
        if (isNameValid.value == true && isPhoneValid.value == true && isEmailValid.value == true && isPhotoValid.value == true) {
            viewModelScope.launch {
                _userRegistration.value = createUserUseCase.invoke(
                    CreateUserUseCase.Params(
                        params = UserBody(
                            name = _name.value,
                            email = _email.value,
                            phone = _phone.value,
                            positionID = selectedPosition.value?.id,
                            photo = photo.value
                        )
                    )
                )
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
    }

}