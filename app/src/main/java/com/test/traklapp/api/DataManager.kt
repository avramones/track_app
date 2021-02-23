package com.test.traklapp.api

import com.test.traklapp.model.Response
import io.reactivex.Observable

class DataManager {

    private val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)

    fun getSearchResults(
        term: String?,
        entity: String?
    ): Observable<Response?>? {
        return apiService.getSearchResults(term, entity)
    }

}