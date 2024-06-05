package com.somed.repository

import com.somed.model.AuthResponse
import com.somed.model.SignInParams
import com.somed.model.SignUpParams
import com.somed.utils.Response

interface UserRepository {
    suspend fun signUp(params:SignUpParams): Response<AuthResponse>
    suspend fun signIn(params:SignInParams):Response<AuthResponse>
}