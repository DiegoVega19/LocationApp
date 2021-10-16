package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Evaluation(
    @SerializedName("total")
    val totalEvaluacions: String = "",
    val nombreEvaluado: String = "",
    val nombreLocal: String = "",
    val fechaDeEvaluacion: String = ""

)