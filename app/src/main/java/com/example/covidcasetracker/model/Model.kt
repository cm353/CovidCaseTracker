package com.example.mycomposeapplication

import com.google.gson.annotations.SerializedName

data class TopLevelWrapper(@SerializedName("Global")var global: Global,
                      @SerializedName("Countries") val counties: Array<Country>,
                      @SerializedName("Date")var date: String)

data class Global(@SerializedName("NewConfirmed")var newConfirmed: String,
             @SerializedName("TotalConfirmed")var totalConfirmed: String,
             @SerializedName("TotalDeaths")var totalDeaths: String,
             @SerializedName("TotalRecovered")var totalRecovered: String,
             @SerializedName("Date")var date: String) {


}

data class Country(@SerializedName("Country")var country:String,
              @SerializedName("NewConfirmed")var newConfirmed: String,
              @SerializedName("TotalConfirmed")var totalConfirmed: String,
              @SerializedName("TotalDeaths")var totalDeaths: String,
              @SerializedName("TotalRecovered")var totalRecovered: String,
              @SerializedName("Date")var date: String)