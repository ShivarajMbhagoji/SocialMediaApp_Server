package com.somed.di

import com.somed.dao.User.UserDao
import com.somed.dao.User.UserDaoImpl
import com.somed.dao.follows.FollowsDao
import com.somed.dao.follows.FollowsDaoImpl
import com.somed.repository.auth.AuthRepository
import com.somed.repository.auth.AuthRepositoryImpl
import com.somed.repository.follows.FollowsRepository
import com.somed.repository.follows.FollowsRepositoryImpl
import org.koin.dsl.module

val appModule= module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
    single<FollowsDao> { FollowsDaoImpl() }
    single<FollowsRepository> { FollowsRepositoryImpl(get(), get()) }
}