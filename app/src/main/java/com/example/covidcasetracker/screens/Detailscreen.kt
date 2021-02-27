package com.example.covidcasetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.mycomposeapplication.Country
import com.google.gson.Gson

@Composable
fun DetailScreen(data: String,cont: NavController) {
    // theme for our app.
    Scaffold(
        // below line we are
        // creating a top bar.
        topBar = {
            AppBar()
        },
        content =  {
            CountryData(data = data)
        }
    )
}

@Composable
fun CountryData(data: String) {
    val dataC = remember { mutableStateOf(Gson().fromJson(data, Country::class.java)) }
    Column() {
        Text(text = dataC.value.country)
        Text(text = dataC.value.totalConfirmed)
        Text(text = dataC.value.newConfirmed)
        Text(text = dataC.value.totalDeaths)
        Text(text = dataC.value.totalRecovered)

    }
}
