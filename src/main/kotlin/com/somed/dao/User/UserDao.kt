package com.somed.dao.User

import com.somed.model.SignUpParams
import com.somed.model.User

interface UserDao{
    suspend fun insert(params: SignUpParams): User?
    suspend fun findByEmail(email: String): User?
}