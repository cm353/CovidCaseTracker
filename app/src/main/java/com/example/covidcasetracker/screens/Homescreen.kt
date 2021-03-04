package com.example.covidcasetracker.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.covidcasetracker.R
import com.example.covidcasetracker.ui.theme.Purple200
import com.example.covidcasetracker.utilFunctions.UtilFunctions
import com.example.covidcasetracker.viewmodel.CovidViewModel
import com.example.mycomposeapplication.Country
import com.google.gson.Gson
import kotlinx.coroutines.launch
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

@Composable
fun Home(cont: NavController, _viewModel: CovidViewModel) {
//    val _viewModel: CovidViewModel = viewModel()
    // imports for observing livedata:
    //import androidx.compose.runtime.livedata.observeAsState
    //import androidx.compose.runtime.getValue
    val myData: List<Country> by _viewModel._localData.observeAsState(arrayListOf())

    // lays out scaffold describing main UI elements of the screen
    Scaffold(
        topBar = {
            AppBar()
        },
        content = {
            CountryDataAsRV( cont, myData)
        },
        floatingActionButton = { RefreshButton {_viewModel.getAll()} }
    )
    _viewModel.getAll()
}

// display the case data of all countries as list (like old Recyclerview)
@Composable
fun CountryDataAsRV(navController: NavController, myData: List<Country>) {
    val grouped = myData.groupBy { it.country[0] }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        grouped.forEach { initial, countries ->
            item {
                CharacterHeader(char= initial, Modifier.fillParentMaxWidth())
            }
            items(countries, itemContent = { item ->
                CountryDataCard(countryData = item, navController)
            })
        }
    }

    val showButton = listState.firstVisibleItemIndex > 0
    ToTopButton(showButton = showButton,  onClick = {
        scope.launch {
            listState.scrollToItem(0)
        }
    })
}

// Go back to first entry of lazylist
@Composable
fun ToTopButton(showButton: Boolean, onClick: () -> Unit) {
    if (showButton) {
        Button(onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.height(64.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors =  ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
            Text(text = "^", style = TextStyle(color = Color.Black) )
        }
    }
}

// round button t
@Composable
fun RefreshButton(onClick: () -> Unit) {
    Button(onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.height(64.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors =  ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
        Image( painter = painterResource(id = R.drawable.cached_24px), contentDescription = "")
    }
}

@Composable
fun CharacterHeader(char: Char, modifier: Modifier) {
    Row(modifier.background(color =  Purple200)) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = char.toString(), color = Color.White)
    }
}

// display data of single country as card
@Composable
fun CountryDataCard(countryData: Country, navController: NavController){
//    val context = LocalContext.current
    Card(
        modifier = Modifier
            .clickable {
//                Toast.makeText(context, "$countrydata", Toast.LENGTH_SHORT).show()
                val countrydataJson = Gson().toJson(countryData)
                navController.navigate("taskDetail/${countrydataJson}")
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
                Text(text = stringResource(id = R.string.country, countryData.country))
            }
            Row() {
                Text(text = stringResource(id = R.string.cases_total, countryData.totalConfirmed))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.cases_new, countryData.newConfirmed))
            }
            Row() {
                Text(text = stringResource(id = R.string.deceased_total, countryData.totalDeaths))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.recovered_total, countryData.totalRecovered))
            }
            Row() {
                val cfr = UtilFunctions.caseFatalityRate(
                    countryData.totalConfirmed.toDouble(),
                    countryData.totalDeaths.toDouble()
                )
                Text(text = stringResource(id = R.string.cfr, cfr))
            }
        }
    }
}

