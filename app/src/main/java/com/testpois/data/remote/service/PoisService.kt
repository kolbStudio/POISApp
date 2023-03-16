package com.testpois.data.remote.service

import com.testpois.data.remote.response.PoisDataDTO
import retrofit2.Response
import retrofit2.http.GET

interface PoisService {
    @GET("/pois.json")
    suspend fun getPois(): Response<PoisDataDTO>
}
