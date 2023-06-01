package com.tamayo.jettes1.domain

import com.tamayo.jettes1.data.model.Data
import com.tamayo.jettes1.data.model.DataResponse
import com.tamayo.jettes1.data.model.Images
import com.tamayo.jettes1.data.model.Original

data class DomainData(
    val images: Images? = null
)


fun List<Data>?.mapToDomain(): List<DomainData> =
    this?.map {
        DomainData(
            images = it.images
        )
    }?: emptyList()
