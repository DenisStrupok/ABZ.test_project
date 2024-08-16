package com.flirtzone.data.repositories

import android.content.Context
import android.net.Uri
import com.abztest.domain.body.UserBody
import com.abztest.domain.models.PositionModel
import com.abztest.domain.models.UserObjectModel
import com.abztest.domain.models.UserRegistrationModel
import com.abztest.domain.repositories.UserRepository
import com.abztest.domain.usecases.user.GetListUsersUseCase
import com.flirtzone.data.response.user.mapObjectToDomain
import com.flirtzone.data.services.token.TokenManger
import com.flirtzone.data.services.user.UserService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException

class UserRepositoryImpl(
    private val userService: UserService,
    private val tokenManager: TokenManger,
    private val context: Context
) : UserRepository {

    override suspend fun getListUsers(params: GetListUsersUseCase.Params): UserObjectModel {
        val response = userService.getListUsers(
            page = params.page ?: 1,
            count = params.count ?: 6
        )
        return with(response) {
            mapObjectToDomain(this)
        }
    }

    override suspend fun getUserPositions(): List<PositionModel> {
        val response = userService.getUserPositions()
        return response.positions.map { it.mapToDomain(it) }
    }

    override suspend fun createUser(body: UserBody): UserRegistrationModel {
        val namePart = body.name?.let { createPartFromString(it) }
        val emailPart = body.email?.let { createPartFromString(it) }
        val phonePart = body.phone?.let { createPartFromString(it) }
        val positionPart = body.positionID?.let { createPartFromString(it.toString()) }
        val photoPart = body.photo?.let { prepareFilePart("photo", it, context = context) }
        val token = tokenManager.getAccessToken()


        val response = photoPart?.let { photo ->
            userService.createUser(
                token = token ?: "",
                name = namePart as RequestBody,
                email = emailPart as RequestBody,
                phone = phonePart as RequestBody,
                positionId = positionPart as RequestBody,
                photo = photo

            )
        }
        return with(response){
            this?.mapToDomain(this)!!
        }
    }

    private fun createPartFromString(value: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, value)
    }

    private fun prepareFilePart(
        partName: String,
        fileUri: String,
        context: Context
    ): MultipartBody.Part {
        val file = File(fileUri)

        if (!file.exists()) {
            throw FileNotFoundException("File not found at path: $fileUri")
        }

        val mimeType = context.contentResolver.getType(Uri.fromFile(file)) ?: "image/jpeg"
        val requestFile = RequestBody.create(MediaType.parse(mimeType), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

}