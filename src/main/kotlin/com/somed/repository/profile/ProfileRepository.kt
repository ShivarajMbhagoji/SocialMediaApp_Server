package com.somed.repository.profile

import com.somed.model.ProfileResponse
import com.somed.model.UpdateUserParams
import com.somed.utils.Response

interface ProfileRepository {

    suspend fun getUserById(userId: Long, currentUserId: Long): Response<ProfileResponse>

    suspend fun updateUser(updateUserParams: UpdateUserParams): Response<ProfileResponse>
}