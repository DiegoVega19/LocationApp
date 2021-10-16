package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("email")
    val emailUser:String,
    val password:String
)