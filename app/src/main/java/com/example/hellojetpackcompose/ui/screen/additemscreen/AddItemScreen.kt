package com.example.hellojetpackcompose.ui.screen.additemscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.hellojetpackcompose.common.UiState
import com.example.hellojetpackcompose.di.Injection
import com.example.hellojetpackcompose.model.User
import com.example.hellojetpackcompose.ui.screen.ViewModelFactory

@Composable
fun AddItemScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: AddItemScreenViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val context = LocalContext.current
    var user: User? = null

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { it ->
        when (it) {
            is UiState.Loading -> user?.let { it1 -> addData(viewModel, it1) }
            is UiState.Success -> {
                Toast.makeText(context, "Success Add Vehicle", Toast.LENGTH_SHORT).show()
                navHostController.navigateUp()
            }

            is UiState.Error -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var driverNameValue by remember { mutableStateOf("") }
        TextField(
            value = driverNameValue,
            onValueChange = { newText ->
                driverNameValue = newText
            },
            label = { Text(text = "Driver Name") },
            placeholder = { Text(text = "Driver Name") },
            modifier = Modifier.padding(16.dp)
        )

        var licenseNumberValue by remember { mutableStateOf("") }
        TextField(
            value = licenseNumberValue,
            onValueChange = { newText ->
                licenseNumberValue = newText
            },
            label = { Text(text = "License Number") },
            placeholder = { Text(text = "License Number") },
            modifier = Modifier.padding(16.dp)
        )

        var dateEnteringValue by remember { mutableStateOf("") }
        TextField(
            value = dateEnteringValue,
            onValueChange = { newText ->
                dateEnteringValue = newText
            },
            label = { Text(text = "Date Entering") },
            placeholder = { Text(text = "Date Entering") },
            modifier = Modifier.padding(16.dp)
        )

        var tonnageWeightValue by remember { mutableStateOf("") }
        TextField(
            value = tonnageWeightValue,
            onValueChange = { newText ->
                tonnageWeightValue = newText
            },
            label = { Text(text = "Tonnage Weight") },
            placeholder = { Text(text = "Tonnage Weight") },
            modifier = Modifier.padding(16.dp)
        )

        var netWeightValue by remember { mutableStateOf("") }
        TextField(
            value = netWeightValue,
            onValueChange = { newText ->
                netWeightValue = newText
            },
            label = { Text(text = "Net Weight") },
            placeholder = { Text(text = "Net Weight") },
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = {
            if (driverNameValue.isNotEmpty() && licenseNumberValue.isNotEmpty() && dateEnteringValue.isNotEmpty() && tonnageWeightValue.isNotEmpty() && netWeightValue.isNotEmpty()) {
                user = User(
                    driverNameValue,
                    licenseNumberValue,
                    dateEnteringValue,
                    tonnageWeightValue,
                    netWeightValue,
                    "https://as2.ftcdn.net/v2/jpg/03/34/50/31/1000_F_334503102_wgJVKIBAZW3dOUKbtl4VkHuH3zkvqkrc.jpg"
                )
                addData(viewModel, user!!)
            } else {
                Toast.makeText(context, "There is an empty input", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Add Data")
        }
    }
}

fun addData(viewModel: AddItemScreenViewModel, user: User) {
    viewModel.addItemVehicle(user)
}
