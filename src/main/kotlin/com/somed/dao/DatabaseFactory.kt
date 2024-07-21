package com.somed.dao

import com.somed.dao.User.UserTable
import com.somed.dao.follows.FollowsTable
import com.somed.dao.post.PostTable
import com.somed.dao.post_like.PostLikesTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(){

        Database.connect(createHikariDataSource())
        transaction {
            SchemaUtils.create(UserTable, FollowsTable, PostTable, PostLikesTable)
        }
    }

    private fun createHikariDataSource():HikariDataSource{
        val driverClass="org.postgresql.Driver"
        val jdbcUrl="jdbc:postgresql://localhost:5432/postgres"

        val hikariConfig=HikariConfig().apply {
            driverClassName=driverClass
            setJdbcUrl(jdbcUrl)
            maximumPoolSize=3
            isAutoCommit=false
            transactionIsolation="TRANSACTION_REPEATABLE_READ"
            username="postgres"
            password="Shivu121314"
            validate()
        }

        return HikariDataSource(hikariConfig)
    }

    suspend fun <T> dbQuery(block : suspend () -> T) =
        newSuspendedTransaction { block() }
}