package com.andreste.fetch.models

import kotlinx.serialization.Serializable

@Serializable
data class HiringItem(
    val id: Int,
    val listId: Int,
    val name: String?
)
