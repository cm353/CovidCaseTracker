package com.example.covidcasetracker.screens

import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.covidcasetracker.R
import com.example.covidcasetracker.utilFunctions.UtilFunctions
import com.example.mycomposeapplication.Country
import com.google.gson.Gson

@Composable
fun DetailScreen(data: String, cont: NavController) {
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
    val cfr = UtilFunctions.caseFatalityRate(
        dataC.value.totalConfirmed.toDouble(),
        dataC.value.totalDeaths.toDouble()
    )


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =  Arrangement.Center
        ) {
            Text(text = stringResource(R.string.country, dataC.value.country))
            Text(text = stringResource(R.string.cases_total, dataC.value.totalConfirmed))
            Text(text = stringResource(R.string.cases_new, dataC.value.newConfirmed))
            Text(text = stringResource(R.string.deceased_total, dataC.value.totalDeaths))
            Text(text = stringResource(R.string.recovered_total, dataC.value.totalRecovered))
            Text(text = stringResource(R.string.cfr, cfr))
        }

}

@Preview
@Composable
fun Test() {
    Scaffold(
        // below line we are
        // creating a top bar.
        topBar = {
            AppBar()
        },
        content =  {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.Center
            ) {
                Text(text = "stringResource")
                Text(text = "stringResource 2")
            }
        }
    )
}

