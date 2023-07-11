package com.apex.codeassesment.data.remote

import javax.inject.Inject

// TODO (2 points): Add tests
class RemoteDataSource @Inject constructor(
    private val api: APIs
) {

    // TODO (5 points): Load data from endpoint https://randomuser.me/api
    suspend fun loadUser() = api.getUsers()

    // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
    // TODO (Optional Bonus: 3 points): Handle success and failure from endpoints
    suspend fun loadUsers() = api.getUsers(10)
}
