package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName(value = "author")
    val author: String,

    @SerialName(value = "author_details")
    val authorDetails: AuthorDetails,

    @SerialName(value = "content")
    val content: String,

    @SerialName(value = "created_at")
    val createdAt: String,

    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "updated_at")
    val updatedAt: String,

    @SerialName(value = "url")
    val url: String
)

@Serializable
data class AuthorDetails(
    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "username")
    val username: String,

    @SerialName(value = "avatar_path")
    val avatarPath: String?,

    @SerialName(value = "rating")
    val rating: Double? // 평가가 없을 수도 있으므로 Nullable로 선언
)