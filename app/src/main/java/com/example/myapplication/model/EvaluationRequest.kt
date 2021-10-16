package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class EvaluationRequest(
    @SerializedName("departamentoId")
    val departamentoID: Int,
    val fechaDeEvaluacion: String,
    val nombreEvaluado: String,
    val pregunta1: String,
    val pregunta2: String,
    val pregunta3: String,
    val pregunta4: String,
    val pregunta5: String,
    val pregunta6: String,
    val pregunta7: String,
    val pregunta8: String
)