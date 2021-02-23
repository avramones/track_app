package com.test.traklapp.api

import com.test.traklapp.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

        @Headers("Content-Type: application/json")
        @GET("search")
        fun getSearchResults(
            @Query("term") searchTerm: String?,
            @Query("entity") entityType: String?
        ): Observable<Response?>?
}