package com.apex.codeassesment.ui

import android.accounts.NetworkErrorException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.apex.codeassesment.data.remote.State
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class Utils {
    companion object{
        fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
            val intent = Intent(this, it)
            intent.putExtras(Bundle().apply(extras))
            startActivity(intent)
        }

        fun resolveError(e: Exception): State.ErrorState {
            var error = e

            when (e) {
                is SocketTimeoutException -> {
                    error = NetworkErrorException()
                }
                is ConnectException -> {
                    error = NetworkErrorException()
                }
                is UnknownHostException -> {
                    error = NetworkErrorException()
                }
            }

            if(e is HttpException){
                when(e.code()){
                    502 -> {
                        error = NetworkErrorException()
                    }
                    401 -> {
                        throw Exception()
                    }
                    400 -> {
                        error = NetworkErrorException()
                    }
                }
            }


            return State.ErrorState(error)
        }
    }
}
