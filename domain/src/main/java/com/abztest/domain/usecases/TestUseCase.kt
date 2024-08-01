package com.abztest.domain.usecases

import com.abztest.domain.usecases.base.BaseUseCase

class TestUseCase : BaseUseCase<TestUseCase.Params, Boolean> {

    class Params

    override suspend fun invoke(params: Params): Boolean {
        return true
    }
}