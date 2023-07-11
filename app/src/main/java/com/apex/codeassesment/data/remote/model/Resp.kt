package com.apex.codeassesment.data.remote.model

import com.apex.codeassesment.data.model.Dob
import com.apex.codeassesment.data.model.Id
import com.apex.codeassesment.data.model.Location
import com.apex.codeassesment.data.model.Login
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.Picture
import com.apex.codeassesment.data.model.Registered
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Resp(
    @SerializedName("gender")
    @Expose
    var gender: String? = null,

    @SerializedName("name")
    @Expose
    var name: Name? = null,

    @SerializedName("location")
    @Expose
    var location: Location? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("login")
    @Expose
    var login: Login? = null,

    @SerializedName("dob")
    @Expose
    var dob: Dob? = null,

    @SerializedName("registered")
    @Expose
    var registered: Registered? = null,

    @SerializedName("phone")
    @Expose
    var phone: String? = null,

    @SerializedName("cell")
    @Expose
    var cell: String? = null,

    @SerializedName("id")
    @Expose
    var id: Id? = null,

    @SerializedName("picture")
    @Expose
    var picture: Picture? = null,

    @SerializedName("nat")
    @Expose
    var nat: String? = null
)
