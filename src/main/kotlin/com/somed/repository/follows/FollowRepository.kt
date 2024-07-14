package com.somed.repository.follows

import com.somed.model.FollowAndUnfollowResponse
import com.somed.utils.Response

interface FollowsRepository {
    suspend fun followUser(follower: Long, following: Long): Response<FollowAndUnfollowResponse>

    suspend fun unfollowUser(follower: Long, following: Long): Response<FollowAndUnfollowResponse>
}