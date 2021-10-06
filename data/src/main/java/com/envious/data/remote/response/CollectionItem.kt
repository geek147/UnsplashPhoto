package com.envious.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class CollectionItem(
    @Json(name = "blur_hash")
    val blurHash: String? = null,
    @Json(name = "color")
    val color: String? = null,
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any?>? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "height")
    val height: Int? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null,
    @Json(name = "likes")
    val likes: Int? = null,
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    @Json(name = "urls")
    val urls: Urls? = null,
    @Json(name = "user")
    val user: User? = null,
    @Json(name = "width")
    val width: Int? = null
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "download")
        val download: String? = null,
        @Json(name = "download_location")
        val downloadLocation: String? = null,
        @Json(name = "html")
        val html: String? = null,
        @Json(name = "self")
        val self: String? = null
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Urls(
        @Json(name = "full")
        val full: String? = null,
        @Json(name = "raw")
        val raw: String? = null,
        @Json(name = "regular")
        val regular: String? = null,
        @Json(name = "small")
        val small: String? = null,
        @Json(name = "thumb")
        val thumb: String? = null
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "bio")
        val bio: String? = null,
        @Json(name = "id")
        val id: String? = null,
        @Json(name = "instagram_username")
        val instagramUsername: String? = null,
        @Json(name = "links")
        val links: Links? = null,
        @Json(name = "location")
        val location: String? = null,
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "portfolio_url")
        val portfolioUrl: String? = null,
        @Json(name = "profile_image")
        val profileImage: ProfileImage? = null,
        @Json(name = "total_collections")
        val totalCollections: Int? = null,
        @Json(name = "total_likes")
        val totalLikes: Int? = null,
        @Json(name = "total_photos")
        val totalPhotos: Int? = null,
        @Json(name = "twitter_username")
        val twitterUsername: String? = null,
        @Json(name = "username")
        val username: String? = null
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "html")
            val html: String? = null,
            @Json(name = "likes")
            val likes: String? = null,
            @Json(name = "photos")
            val photos: String? = null,
            @Json(name = "portfolio")
            val portfolio: String? = null,
            @Json(name = "self")
            val self: String? = null
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class ProfileImage(
            @Json(name = "large")
            val large: String? = null,
            @Json(name = "medium")
            val medium: String? = null,
            @Json(name = "small")
            val small: String? = null
        )
    }
}