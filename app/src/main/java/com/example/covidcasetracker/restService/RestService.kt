package com.example.covidcasetracker.restService

import com.example.mycomposeapplication.TopLevelWrapper
import retrofit2.Call
import retrofit2.http.GET

interface RestService {

    @GET("summary")
    fun getAll() : Call<TopLevelWrapper>

}