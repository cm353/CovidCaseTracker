package com.example.covidcasetracker.screens

import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.covidcasetracker.R

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
                text = stringResource(id = R.string.app_name),
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