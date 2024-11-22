package com.andreste.fetch.services

import com.andreste.fetch.models.HiringItem
import retrofit2.Response
import retrofit2.http.GET

interface HiringService {

    @GET("hiring.json")
    suspend fun getItems(): Response<List<HiringItem>>

}