package com.flirtzone.data.response.user

import com.abztest.domain.models.LinksModel
import com.abztest.domain.models.UserModel
import com.abztest.domain.models.UserObjectModel
import com.google.gson.annotations.SerializedName

data class UsersObjectResponse(
    val success: Boolean?,
    val page: Int?,
    val totalPage: Int?,
    val totalUsers: Int?,
    val count: Int?,
    val links: LinksResponse?,
    val users: MutableList<UserResponse>?
)

fun mapObjectToDomain(response: UsersObjectResponse): UserObjectModel {
    return UserObjectModel(
        success = response.success,
        page = response.page,
        totalPage = response.totalPage,
        totalUsers = response.totalUsers,
        count = response.count,
        links = response.links?.let { mapLinksToDomain(it) },
        users = response.users?.let { mapListToDomain(it) }
    )
}

data class LinksResponse(
    @SerializedName("next_url")
    val nexUrl: String?,
    @SerializedName("prev_url")
    val prevUrl: String?
)

data class UserResponse(
    val id: Int?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val position: String?,
    @SerializedName("position_id")
    val positionID: String?,
    @SerializedName("registration_timestamp")
    val registrationTimestamp: Long?,
    val photo: String?
) {

    fun mapToDomain(): UserModel {
        return UserModel(
            id = this.id,
            name = this.name,
            email = this.email,
            phone = this.phone,
            position = this.position,
            positionID = this.positionID,
            registrationTime = this.registrationTimestamp,
            photo = this.photo
        )
    }
}

fun mapListToDomain(list: List<UserResponse>): MutableList<UserModel> {
    return list.map { it.mapToDomain() }.toMutableList()
}

fun mapLinksToDomain(links: LinksResponse): LinksModel {
    return LinksModel(
        nextUrl = links.nexUrl,
        prevUrl = links.prevUrl
    )
}