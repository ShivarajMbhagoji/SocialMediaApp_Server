package com.somed.dao.User

import com.somed.dao.DatabaseFactory.dbQuery
import com.somed.model.SignUpParams
import com.somed.model.User
import com.somed.model.UserRow
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDaoImpl:UserDao {
    override suspend fun insert(params: SignUpParams): User? {
        return dbQuery{
            val insertStatement = UserRow.insert {
                it[name] = params.name
                it[email] = params.email
                it[password] = params.password
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): User? {
        return dbQuery {
            UserRow.select( UserRow.email eq email)
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    private fun rowToUser(row:ResultRow) :User{
        return User(
            id = row[UserRow.id],
            name = row[UserRow.name],
            bio = row[UserRow.bio],
            avatar = row[UserRow.avatar],
            password = row[UserRow.password]
        )
    }
}