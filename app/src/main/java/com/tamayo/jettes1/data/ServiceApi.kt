package com.tamayo.jettes1.data

import com.tamayo.jettes1.data.model.Data
import com.tamayo.jettes1.data.model.DataResponse
import com.tamayo.jettes1.utils.API_KEY
import com.tamayo.jettes1.utils.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {


    //https://api.giphy.com/v1/gifs/search?api_key=WeuzUN3GFSukJMOwXNlLE94HvlgmBZfV&q=cats

    @GET(ENDPOINT)
    suspend fun getData(
        @Query("q") q: String? = null,
        @Query("api_key") api_key: String = API_KEY
    ): Response<DataResponse>


}