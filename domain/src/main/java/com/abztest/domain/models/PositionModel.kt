package com.abztest.domain.models

data class PositionModel(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)