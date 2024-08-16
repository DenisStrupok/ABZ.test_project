package com.flirtzone.data.response.user

import com.abztest.domain.models.UserRegistrationModel
import com.google.gson.annotations.SerializedName

data class UserRegistrationResponse(
    val success: Boolean,
    @SerializedName("user_id")
    val userId: String
) {

    fun mapToDomain(userRegistrationResponse: UserRegistrationResponse): UserRegistrationModel {
        return UserRegistrationModel(
            isSuccess = userRegistrationResponse.success,
            id = userRegistrationResponse.userId
        )
    }
}
