package com.apex.codeassesment.data.local

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.User.Companion.createRandom
import com.apex.codeassesment.data.remote.mapper.RemoteToLocalDataMapper.toUser
import com.apex.codeassesment.data.remote.model.Users
import com.squareup.moshi.Moshi
import javax.inject.Inject

// TODO (3 points): Convert to Kotlin
// TODO (2 point): Add tests
// TODO (1 point): Use the correct naming conventions.
// TODO (3 points): Inject all dependencies instead of instantiating them.
class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val moshi: Moshi
) {

    fun loadUser(): User {
        val serializedUser = preferencesManager.loadUser()
        val jsonAdapter = moshi.adapter(
            User::class.java
        )
        return try {
            // TODO (1 point): Address Android Studio warning
            val user = if (serializedUser != null) jsonAdapter.fromJson(serializedUser) else null
            user ?: createRandom()
        } catch (e: Exception) {
            e.printStackTrace()
            createRandom()
        }
    }

    fun saveUsersList(users: Users): User {
        var user = User()
        if (!users.resps.isNullOrEmpty()) {
            users.resps.forEach {
                user = it.toUser()
                saveUser(it.toUser())
            }
        }
        return user
    }

    private fun saveUser(user: User) {
        val jsonAdapter = moshi.adapter(
            User::class.java
        )
        val serializedUser = jsonAdapter.toJson(user)
        preferencesManager.saveUser(serializedUser)
    }
}
