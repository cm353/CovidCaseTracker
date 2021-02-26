package com.example.covidcasetracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.covidcasetracker.ui.theme.CovidCaseTrackerTheme
import com.example.covidcasetracker.utilFunctions.UtilFunctions
import com.example.covidcasetracker.viewmodel.CovidViewModel
import com.example.mycomposeapplication.Country
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue


class MainActivity : AppCompatActivity() {

    val _viewModel: CovidViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CovidCaseTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Screen(_viewModel = _viewModel)
                }
            }
        }
        _viewModel.getAll()
    }
}

// UI of this screen
@Composable
fun Screen(_viewModel: CovidViewModel) {
    // theme for our app.
    Scaffold(
        // below line we are
        // creating a top bar.
        topBar = {
            AppBar()
        },
        content =  {
            CountryDataAsRV(_viewModel = _viewModel)
        }
    )
}

// display the case data of all countries as list (like old Recyclerview)
// store data in viewmodel and display on change
// will be called once and this composable oberve then the livedata object
@Composable
fun CountryDataAsRV(_viewModel: CovidViewModel) {
    // imports for observing livedata:
    //import androidx.compose.runtime.livedata.observeAsState
    //import androidx.compose.runtime.getValue
    val myData: List<Country> by _viewModel._localData.observeAsState(arrayListOf())

    LazyColumn() {
        items(myData, itemContent = { item ->
            CountryDataCard(countrydata = item)
        })
    }
}

// display data of single country as card
@Composable
fun CountryDataCard(countrydata: Country){
    val context = LocalContext.current
    Card(
        Modifier
            .clickable {
                Toast
                    .makeText(context, "$countrydata", Toast.LENGTH_SHORT)
                    .show()
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
                    countrydata.totalConfirmed.toDouble()
                    , countrydata.totalDeaths.toDouble()
                )
                Text(text = stringResource(id = R.string.cfr, cfr))
            }
        }
    }
}

// display the appbar
@Composable
fun AppBar() {
    TopAppBar(
        // in below line we are
        // adding title to our top bar.
        title = {
            // inside title we are
            // adding text to our toolbar.
            Text(
                text = "Covid-19 Cases",
                // below line is use
                // to give text color.
                color = Color.White
            )
        },
        // below line is use to give background color
        backgroundColor = colorResource(id = R.color.purple_500),

        // content color is use to give
        // color to our content in our toolbar.
        contentColor = Color.White,

        // below line is use to give
        // elevation to our toolbar.
        elevation = 12.dp
    )
}
