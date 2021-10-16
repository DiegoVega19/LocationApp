package com.example.myapplication.api

import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.EvaluationRequest
import com.example.myapplication.model.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


    @POST("login")
    suspend fun login(
        @Body login: Login
    ): Response<Login>

    @GET("evaluationCount/1")
    suspend fun getPricesmartEvaluation(): Response<Evaluation>

    @GET("evaluationCount/2")
    suspend fun getPlazaOnceEvaluation(): Response<Evaluation>

    @POST("evaluation")
    suspend fun postEvaluation(
        @Body evaluationRequest: EvaluationRequest
    ): Response<EvaluationRequest>

    @GET("evaluations")
    suspend fun getAllEvaluations(): Response<List<Evaluation>>


    @GET("evaluations/{id}")
    suspend fun getEvaluationsById(
        @Path("id") id: Int
    ): Response<List<Evaluation>>


}