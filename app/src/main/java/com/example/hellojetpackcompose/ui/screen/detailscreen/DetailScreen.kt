package com.example.hellojetpackcompose.ui.screen.detailscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hellojetpackcompose.R
import com.example.hellojetpackcompose.common.UiState
import com.example.hellojetpackcompose.di.Injection
import com.example.hellojetpackcompose.ui.screen.ViewModelFactory

/**
 * Created by Alo-BambangHariantoSianturi on 26/10/23.
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    heroId: String, viewModel: DetailScreenViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> viewModel.getHeroById(heroId)
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.name.orEmpty(),
                    data.licenseNumber.orEmpty(),
                    data.dateEntering.orEmpty(),
                    data.netWeight.orEmpty(),
                    data.photoUrl.orEmpty(),
                    modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    licenseNumber: String,
    dateEntering: String,
    netWeight: String,
    photoUrl: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            AsyncImage(
                model = photoUrl,
                placeholder = painterResource(id = R.drawable.jetpack_compose),
                error = painterResource(id = R.drawable.jetpack_compose),
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
                contentDescription = "The design logo",
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = "Driver Name : $name",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Number Of Vehicle : $licenseNumber",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )

            Text(
                text = "Date Entering Vehicle : $dateEntering",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )

            Text(
                text = "Net Weight Of Vehicle : $netWeight",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
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

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(heroId = "1")
}