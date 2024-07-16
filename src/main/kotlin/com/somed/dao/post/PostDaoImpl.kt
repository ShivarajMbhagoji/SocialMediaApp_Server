package com.somed.dao.post

import com.somed.dao.DatabaseFactory.dbQuery
import com.somed.dao.User.UserTable
import com.somed.utils.IdGenerator
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

class PostDaoImpl : PostDao {
    override suspend fun createPost(caption: String, imageUrl: String, userId: Long): Boolean {
        return dbQuery{
            val insertStatement = PostTable.insert {
                it[postId] = IdGenerator.generateId()
                it[PostTable.caption] = caption
                it[PostTable.imageUrl] = imageUrl
                it[likesCount] = 0
                it[commentsCount] = 0
                it[PostTable.userId] = userId
            }
            insertStatement.resultedValues?.singleOrNull() != null
        }
    }

    override suspend fun getFeedsPost(
        userId: Long,
        follows: List<Long>,
        pageNumber: Int,
        pageSize: Int
    ): List<PostRow> {
        return dbQuery {
            if (follows.size > 1){
                getPosts(users = follows, pageSize = pageSize, pageNumber = pageNumber)
            }else{
                PostTable
                    .join(
                        otherTable = UserTable,
                        onColumn = PostTable.userId,
                        otherColumn = UserTable.id,
                        joinType = JoinType.INNER
                    )
                    .selectAll()
                    .orderBy(column = PostTable.likesCount, order = SortOrder.DESC)
                    .limit(n = pageSize, offset = ((pageNumber -1) * pageSize).toLong())
                    .map { toPostRow(it) }
            }
        }
    }

    override suspend fun getPostByUser(userId: Long, pageNumber: Int, pageSize: Int): List<PostRow> {
        return dbQuery {
            getPosts(users = listOf(userId), pageSize = pageSize, pageNumber = pageNumber)
        }
    }

    override suspend fun getPost(postId: Long): PostRow? {
        return dbQuery {
            PostTable
                .join(
                    otherTable = UserTable,
                    onColumn = PostTable.userId,
                    otherColumn = UserTable.id,
                    joinType = JoinType.INNER
                )
                .select { PostTable.postId eq postId }
                .singleOrNull()
                ?.let { toPostRow(it) }
        }
    }

    override suspend fun deletePost(postId: Long): Boolean {
        return dbQuery {
            PostTable.deleteWhere { PostTable.postId eq postId } > 0
        }
    }

    private fun getPosts(users: List<Long>, pageSize: Int, pageNumber: Int): List<PostRow>{
        return PostTable
            .join(
                otherTable = UserTable,
                onColumn = PostTable.userId,
                otherColumn = UserTable.id,
                joinType = JoinType.INNER
            )
            .select(where = PostTable.userId inList users)
            .orderBy(column = PostTable.createdAt, order = SortOrder.DESC)
            .limit(n = pageSize, offset = ((pageNumber -1) * pageSize).toLong())
            .map { toPostRow(it) }
    }

    private fun toPostRow(row: ResultRow): PostRow{
        return PostRow(
            postId = row[PostTable.postId],
            caption = row[PostTable.caption],
            imageUrl = row[PostTable.imageUrl],
            createdAt = row[PostTable.createdAt].toString(),
            likesCount = row[PostTable.likesCount],
            commentsCount = row[PostTable.commentsCount],
            userId = row[PostTable.userId],
            userName = row[UserTable.name],
            userImageUrl = row[UserTable.imageUrl]
        )
    }
}