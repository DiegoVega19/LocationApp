package com.example.myapplication.repositori

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.EvaluationRequest
import com.example.myapplication.model.Login
import retrofit2.Response

class MainRepository {

    //Lo ideal es crear un repositorio para cada view Model o seccion de la aplicacion,
    //Pero esta aplicacion es de corto alcance


    suspend fun loginUser(login: Login): Response<Login> {
        return RetrofitInstance.api.login(login)
    }

    suspend fun getCountPricesmart(): Response<Evaluation> {
        return RetrofitInstance.api.getPricesmartEvaluation()
    }

    suspend fun getCountPlazaOnce(): Response<Evaluation> {
        return RetrofitInstance.api.getPlazaOnceEvaluation()
    }

    suspend fun postEvaluation(evaluationRequest: EvaluationRequest): Response<EvaluationRequest> {
        return RetrofitInstance.api.postEvaluation(evaluationRequest)
    }

    suspend fun getAllEvaluations(): Response<List<Evaluation>> {
        return RetrofitInstance.api.getAllEvaluations()
    }

    suspend fun getEvaluationsById(id: Int): Response<List<Evaluation>> {
        return RetrofitInstance.api.getEvaluationsById(id)
    }

}