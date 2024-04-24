package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "site")
    val site: String,

    @SerialName(value = "key")
    val key: String
)
