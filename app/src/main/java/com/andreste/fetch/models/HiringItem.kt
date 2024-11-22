package com.andreste.fetch.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HiringItem(
    val id: Int,
    @SerialName("listId") val listId: Int? = null,
    val name: String?
)
