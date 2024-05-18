package com.example.hellojetpackcompose.ui.screen.listscreen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.hellojetpackcompose.common.UiState
import com.example.hellojetpackcompose.di.Injection
import com.example.hellojetpackcompose.model.User
import com.example.hellojetpackcompose.ui.screen.ScrollToTopButton
import com.example.hellojetpackcompose.ui.screen.ViewModelFactory
import com.example.hellojetpackcompose.ui.screen.showToast
import kotlinx.coroutines.launch

/**
 * Created by Alo-BambangHariantoSianturi on 03/11/23.
 */

@Composable
fun ListHeroScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (User) -> Unit,
    onClickFabButton: () -> Unit,
    viewModel: ListHeroScreenViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { it ->
        when (it) {
            is UiState.Loading -> viewModel.getHeroes()
            is UiState.Success -> {
                val data = it.data.sortedBy { it.name }.groupBy { it.name!![0] }
                SetListContent(
                    modifier = modifier,
                    data = data,
                    onNextButtonClicked = onNextButtonClicked,
                    onClickFabButton = onClickFabButton
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetListContent(
    modifier: Modifier,
    data: Map<Char, List<User>>,
    onNextButtonClicked: (User) -> Unit,
    onClickFabButton: () -> Unit
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 80.dp)) {
            if (data.isNotEmpty()) {
                data.forEach { (initial, heroes) ->
                    stickyHeader {
                        CharacterHeader(initial)
                    }
                    items(heroes, key = { it.name.orEmpty() }) { hero ->
                        HeroListItem(
                            name = hero.name.orEmpty(),
                            licenseNumber = hero.licenseNumber.orEmpty(),
                            dateEntering = hero.dateEntering.orEmpty(),
                            tonnageWeight = hero.tonnageWeight.orEmpty(),
                            photoUrl = hero.photoUrl.orEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100)),
                            selectedHero = {
                                onNextButtonClicked(hero)
                            }
                        )
                    }
                }
            }
        }

        val context = LocalContext.current
        Box(
            modifier = Modifier
                .padding(bottom = 30.dp, end = 30.dp)
                .align(Alignment.BottomEnd)
        ) {
            SmallExample(context, onClickFabButton, modifier)
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(
                    Alignment.BottomCenter
                )
        ) {
            ScrollToTopButton(onClick = { scope.launch { listState.scrollToItem(index = 0) } })
        }
    }
}

@Composable
fun SmallExample(context: Context, onClickFabButton: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        onClick = {
            onClickFabButton()
        },
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}

@Composable
fun HeroListItem(
    name: String,
    licenseNumber: String,
    dateEntering: String,
    tonnageWeight: String,
    photoUrl: String,
    modifier: Modifier = Modifier,
    selectedHero: (String) -> Unit,
) {
    val context = LocalContext.current

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.clickable {
            selectedHero(name)
            showToast(context, name)
        })
        {
            SubcomposeAsyncImage(model = photoUrl,
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        selectedHero(name)
                        showToast(context, name)
                    }
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                loading = { CircularProgressIndicator(strokeWidth = 4.dp) })

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Driver Name : $name",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )

                Text(
                    text = "License Number: $licenseNumber",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )

                Text(
                    text = "Date Entering: $dateEntering",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )

                Text(
                    text = "Weight: $tonnageWeight",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }

        }
    }
}

@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}