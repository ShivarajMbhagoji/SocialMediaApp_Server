package com.somed.repository.post

import com.somed.model.PostResponse
import com.somed.model.PostTextParams
import com.somed.model.PostsResponse
import com.somed.utils.Response

interface PostRepository {
    suspend fun createPost(imageUrl: String, postTextParams: PostTextParams): Response<PostResponse>

    suspend fun getFeedPosts(userId: Long, pageNumber: Int, pageSize: Int): Response<PostsResponse>

    suspend fun getPostsByUser(
        postsOwnerId: Long,
        currentUserId: Long,
        pageNumber: Int,
        pageSize: Int
    ): Response<PostsResponse>

    suspend fun getPost(postId: Long, currentUserId: Long): Response<PostResponse>

    suspend fun deletePost(postId: Long): Response<PostResponse>
}