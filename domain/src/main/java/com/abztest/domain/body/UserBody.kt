package com.abztest.domain.body

data class UserBody(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val positionID: Int? = null,
    val photo: String? = null
)