package com.somed.dao.post_like

interface PostLikesDao {
    suspend fun addLike(postId: Long, userId: Long): Boolean

    suspend fun removeLike(postId: Long, userId: Long): Boolean

    suspend fun isPostLikedByUser(postId: Long, userId: Long): Boolean
}