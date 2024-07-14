package com.somed.dao.User

import com.somed.model.SignUpParams

interface UserDao{
    suspend fun insert(params: SignUpParams): UserRow?
    suspend fun findByEmail(email: String): UserRow?

    suspend fun updateFollowsCount(follower: Long, following: Long, isFollowing: Boolean): Boolean
}