package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    @SerialName(value = "results")
    var details: Details
)
