package com.testpois.features.getPois.data.service

import com.testpois.features.getPois.data.model.response.PoisDataDTO
import retrofit2.Response
import retrofit2.http.GET

interface PoisService {
    @GET("/pois.json")
    suspend fun getPois(): Response<PoisDataDTO>
}
