package com.apex.codeassesment.data

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// TODO (2 points) : Add tests
// TODO (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings
class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    private val savedUser = AtomicReference(User())

    fun getSavedUser() = localDataSource.loadUser()

    suspend fun getUser(forceUpdate: Boolean): User {
        if (forceUpdate) {
            val users = withContext(Dispatchers.IO) {
                remoteDataSource.loadUser()
            }
            savedUser.set(localDataSource.saveUsersList(users))
        }
        return savedUser.get()
    }

    suspend fun getUsers() =  withContext(Dispatchers.IO) {
        remoteDataSource.loadUsers()
    }
}
