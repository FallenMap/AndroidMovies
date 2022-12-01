package com.davilav.wigilabstest.data.remote

import com.davilav.wigilabstest.data.model.ErrorResponse
import com.davilav.wigilabstest.data.model.Page
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("api_key", encoded = true) api_key: String,
        @Query("language", encoded = true) language: String
    ) : NetworkResponse<Page, ErrorResponse>
}