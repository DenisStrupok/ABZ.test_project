package com.abztest.domain.models


data class UserObjectModel(
    val success: Boolean?,
    val page: Int?,
    val totalPage: Int?,
    val totalUsers: Int?,
    val count: Int?,
    val links: LinksModel?,
    val users: List<UserModel>?
)

data class LinksModel(
    val nextUrl: String?,
    val prevUrl: String?
)

data class UserModel(
    val id: Int? = null,
    val name: String?,
    val email: String?,
    val phone: String?,
    val position: String? = null,
    val positionID: String? = null,
    val registrationTime: Long? = null,
    val photo: String?
)
