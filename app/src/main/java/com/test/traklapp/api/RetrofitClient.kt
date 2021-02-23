package com.test.traklapp.api

import com.google.gson.GsonBuilder
import com.test.traklapp.utils.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: Retrofit
        get() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create()
                )
            )
            .client(OkHttpClient())
            .baseUrl(AppConstants.BASE_URL)
            .build()
}