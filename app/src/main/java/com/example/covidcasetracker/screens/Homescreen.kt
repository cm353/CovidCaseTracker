package com.example.covidcasetracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.covidcasetracker.R
import com.example.covidcasetracker.utilFunctions.UtilFunctions
import com.example.covidcasetracker.viewmodel.CovidViewModel
import com.example.mycomposeapplication.Country
import com.google.gson.Gson

@Composable
fun Home(_viewModel: CovidViewModel, cont: NavController) {
    // theme for our app.
    Scaffold(
        // below line we are
        // creating a top bar.
        topBar = {
            AppBar()
        },
        content =  {
            CountryDataAsRV(_viewModel = _viewModel, cont)
        }
    )
}

// display the case data of all countries as list (like old Recyclerview)
// store data in viewmodel and display on change
// will be called once and this composable observe then the livedata object
@Composable
fun CountryDataAsRV(_viewModel: CovidViewModel, cont: NavController) {
    // imports for observing livedata:
    //import androidx.compose.runtime.livedata.observeAsState
    //import androidx.compose.runtime.getValue
    val myData: List<Country> by _viewModel._localData.observeAsState(arrayListOf())

    LazyColumn() {
        items(myData, itemContent = { item ->
            CountryDataCard(countrydata = item, cont)
        })
    }
}

// display data of single country as card
@Composable
fun CountryDataCard(countrydata: Country, cont: NavController){
//    val context = LocalContext.current
    Card(
        modifier = Modifier
            .clickable {
//                Toast.makeText(context, "$countrydata", Toast.LENGTH_SHORT).show()
                val countrydataJson = Gson().toJson(countrydata)
                cont.navigate("taskDetail/${countrydataJson}")
            }
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        contentColor =  MaterialTheme.colors.surface,
        backgroundColor =  MaterialTheme.colors.primary,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row() {
                Text(text = stringResource(id = R.string.country, countrydata.country))
            }
            Row() {
                Text(text = stringResource(id = R.string.cases_total, countrydata.totalConfirmed))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.cases_new, countrydata.newConfirmed))
            }
            Row() {
                Text(text = stringResource(id = R.string.deceased_total, countrydata.totalDeaths))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.recovered_total, countrydata.totalRecovered))
            }
            Row() {
                val cfr = UtilFunctions.caseFatalityRate(
                    countrydata.totalConfirmed.toDouble(),
                    countrydata.totalDeaths.toDouble()
                )
                Text(text = stringResource(id = R.string.cfr, cfr))
            }
        }
    }
}