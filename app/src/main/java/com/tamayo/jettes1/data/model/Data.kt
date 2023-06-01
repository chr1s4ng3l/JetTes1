package com.tamayo.jettes1.data.model

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("data")
    val data: List<Data>? = null,
)

data class Data(
    @SerializedName("images")
    val images: Images? = null
)

data class Images(
    @SerializedName("original")
    val original: Original? = null
)

data class Original(
    @SerializedName("url")
    val url: String? = null
)