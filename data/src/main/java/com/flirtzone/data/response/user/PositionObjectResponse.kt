package com.flirtzone.data.response.user

import com.abztest.domain.models.PositionModel

data class PositionObjectResponse(
    val success: Boolean,
    val positions: List<PositionResponse>
)

data class PositionResponse(
    val id: Int,
    val name: String
) {
    fun mapToDomain(position: PositionResponse): PositionModel{
        return PositionModel(
            id = position.id,
            name = position.name
        )
    }
}