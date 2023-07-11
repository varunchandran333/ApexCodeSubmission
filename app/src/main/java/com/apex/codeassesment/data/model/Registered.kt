package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Registered(
    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("age")
    @Expose
    var age: Int? = null
): Parcelable
