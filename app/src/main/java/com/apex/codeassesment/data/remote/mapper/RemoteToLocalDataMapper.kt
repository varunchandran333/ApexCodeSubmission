package com.apex.codeassesment.data.remote.mapper

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.model.Resp

object RemoteToLocalDataMapper{
    fun Resp.toUser() = User(
        gender, name, location, email, login, dob, registered, phone, cell, id, picture, nat
    )
}
