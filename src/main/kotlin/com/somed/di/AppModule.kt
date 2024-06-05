package com.somed.di

import com.somed.dao.User.UserDao
import com.somed.dao.User.UserDaoImpl
import com.somed.repository.UserRepository
import com.somed.repository.UserRepositoryImpl
import org.koin.dsl.module

val appModule= module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}