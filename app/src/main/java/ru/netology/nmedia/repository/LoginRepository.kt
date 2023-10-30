package ru.netology.nmedia.repository

import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.auth.AuthState
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import java.io.IOException

class LoginRepository {
    suspend fun auth(username: String, password: String): Response<AuthState> {
        val response = PostsApi.service.updateUser(username, password)
        if (!response.isSuccessful) {
            throw ApiError(response.code(), response.message())
        }
        val body = response.body() ?: throw ApiError(response.code(), response.message())
        body.token?.let { AppAuth.getInstance().setAuth(body.id, it) }
        return response
    }
}