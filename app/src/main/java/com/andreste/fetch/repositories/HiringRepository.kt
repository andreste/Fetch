package com.andreste.fetch.repositories

import com.andreste.fetch.services.HiringService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HiringRepository @Inject constructor(
    private val hiringService: HiringService
) {

    suspend fun getItems() = hiringService.getItems()

}