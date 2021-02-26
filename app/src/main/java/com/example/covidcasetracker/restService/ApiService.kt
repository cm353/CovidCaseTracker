package com.example.covidcasetracker.restService

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mycomposeapplication.Country
import com.example.mycomposeapplication.Global
import com.example.mycomposeapplication.TopLevelWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    companion object {
        const val BASE_URL = "https://api.covid19api.com/"
        const val TAG = "ApiService"
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RestService::class.java)

    val globalData = MutableLiveData<Global>()
    val localData = MutableLiveData<List<Country>>()

    // event handler
    var onResponseEvent: ((Any) -> Unit)? = null

    fun getDataFromInternet() {
        val call = service.getAll()
        // Async call
        call.let {
            it.enqueue(object : Callback<TopLevelWrapper> {
                override fun onResponse(call: Call<TopLevelWrapper>, response: Response<TopLevelWrapper>) {
                    val topLevelWrapperObject = response.body()
                    topLevelWrapperObject?.let { data : TopLevelWrapper ->
                        Log.d(TAG, "onResponse Master Wrapper: ${data.global} ${data.date}")
                        globalData.postValue(data.global)
                        localData.postValue(data.counties.asList())
                        for (entry in data.counties) {
                            Log.d(TAG, "onResponse Country: ${entry}")
                        }

                        onResponseEvent?.let {
                            it.invoke("${data.global}")
                        }

                    }

                }

                override fun onFailure(call: Call<TopLevelWrapper>, t: Throwable?) {
                    Log.e(TAG, "onFailure: $t", t)

                    onResponseEvent?.let {
                        it.invoke("Connection Error\n ${t?.localizedMessage}")
                    }
                }
            })
        } ?: run {
            Log.d(TAG, "call object null")
        }
    }
}