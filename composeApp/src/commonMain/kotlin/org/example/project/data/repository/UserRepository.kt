package org.example.project.data.repository

import org.example.project.data.api.UserApi
import org.example.project.domain.model.User

class UserRepository(private val userApi: UserApi) {

    suspend fun getUser(userId: Int): User? {
        return try {
            userApi.getUser(userId)
        } catch (e: Exception) {
            println("Error fetching user: \${e.message}")
            null
        }
    }

    suspend fun getAllUsers(): List<User>? {
        return try {
            userApi.getAllUsers()
        } catch (e: Exception) {
            println("Error fetching users: \${e.message}")
            null
        }
    }

    suspend fun createUser(user: User): User? {
        return try {
            userApi.createUser(user)
        } catch (e: Exception) {
            println("Error creating user: \${e.message}")
            null
        }
    }

    suspend fun updateUser(userId: Int, user: User): User? {
        return try {
            userApi.updateUser(userId, user)
        } catch (e: Exception) {
            println("Error updating user: \${e.message}")
            null
        }
    }

    suspend fun deleteUser(userId: Int): Boolean {
        return try {
            userApi.deleteUser(userId)
        } catch (e: Exception) {
            println("Error deleting user: \${e.message}")
            false
        }
    }
}
