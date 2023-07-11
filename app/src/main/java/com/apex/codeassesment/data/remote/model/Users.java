package com.apex.codeassesment.data.remote.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("results")
    @Expose
    public List<Resp> resps;

}
