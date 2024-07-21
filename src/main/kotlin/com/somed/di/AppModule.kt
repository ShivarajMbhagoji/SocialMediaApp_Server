package com.somed.di

import com.somed.dao.User.UserDao
import com.somed.dao.User.UserDaoImpl
import com.somed.dao.follows.FollowsDao
import com.somed.dao.follows.FollowsDaoImpl
import com.somed.dao.post.PostDao
import com.somed.dao.post.PostDaoImpl
import com.somed.dao.post_like.PostLikesDao
import com.somed.dao.post_like.PostLikesDaoImpl
import com.somed.repository.auth.AuthRepository
import com.somed.repository.auth.AuthRepositoryImpl
import com.somed.repository.follows.FollowsRepository
import com.somed.repository.follows.FollowsRepositoryImpl
import com.somed.repository.post.PostRepository
import com.somed.repository.post.PostRepositoryImpl
import com.somed.repository.profile.ProfileRepository
import com.somed.repository.profile.ProfileRepositoryImpl
import org.koin.dsl.module

val appModule= module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
    single<FollowsDao> { FollowsDaoImpl() }
    single<FollowsRepository> { FollowsRepositoryImpl(get(), get()) }
    single<PostLikesDao> { PostLikesDaoImpl() }
    single<PostDao> { PostDaoImpl() }
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(),get()) }
}