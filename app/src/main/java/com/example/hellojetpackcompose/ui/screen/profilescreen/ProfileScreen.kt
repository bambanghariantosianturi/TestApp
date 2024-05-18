package com.example.hellojetpackcompose.ui.screen.profilescreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hellojetpackcompose.R

/**
 * Created by Alo-BambangHariantoSianturi on 10/11/23.
 */
@Composable
fun ProfileScreen(modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.scrollable(state = scrollState, orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            AsyncImage(
                model = "https://1.bp.blogspot.com/-1TBOZ-u2HXQ/WLjH4oMPFVI/AAAAAAAAHtk/qyfSQVAqlAAYnqjhSLxr1JSyc9xpPFtwwCLcB/s1600/BambangFB.jpg",
                placeholder = painterResource(id = R.drawable.jetpack_compose),
                error = painterResource(id = R.drawable.jetpack_compose),
                modifier = modifier.fillMaxWidth(),
                contentDescription = "Profile Image",
            )
            Text(
                text = "Bambang Harianto Sianturi",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(id = R.string.profile_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
        Row(modifier = Modifier.weight(1f, false)) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.padding_medium)
                )
            ) {


            }
        }
    }
}