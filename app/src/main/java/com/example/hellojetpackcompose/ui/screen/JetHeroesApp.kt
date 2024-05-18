package com.example.hellojetpackcompose.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hellojetpackcompose.R
import com.example.hellojetpackcompose.navigation.Screen
import com.example.hellojetpackcompose.ui.screen.additemscreen.AddItemScreen
import com.example.hellojetpackcompose.ui.screen.detailscreen.DetailScreen
import com.example.hellojetpackcompose.ui.screen.listscreen.HeroListItem
import com.example.hellojetpackcompose.ui.screen.listscreen.ListHeroScreen
import com.example.hellojetpackcompose.ui.screen.profilescreen.ProfileScreen
import com.example.hellojetpackcompose.ui.theme.HelloJetpackComposeTheme

/**
 * Created by Alo-BambangHariantoSianturi on 25/10/23.
 */

@ExperimentalMaterial3Api
@Composable
fun JetHeroesApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    var mTitleBar = ""
    // Get current back stack entry
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = mTitleBar.ifEmpty { stringResource(id = R.string.vehicle_app) },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    if (currentScreen != Screen.Home.route) {
                        IconButton(onClick = { navHostController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "about_page",
                                tint = Color.White
                            )
                        }
                    }
                },
                actions = {
                    if (currentScreen != Screen.Profile.route) {
                        IconButton(onClick = { navHostController.navigate(Screen.Profile.route) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_account_circle_white),
                                contentDescription = "about_page",
                                tint = Color.White
                            )
                        }
                    }
                },
            )
        },

        ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Home.route) {
                ListHeroScreen(modifier = modifier, onNextButtonClicked = { user ->
                    navHostController.navigate(
                        Screen.Detail.createRoute(
                            heroId = user.name.orEmpty(),
                            heroName = user.licenseNumber.orEmpty()
                        )
                    )
                },
                    onClickFabButton = {
                        navHostController.navigate(
                            Screen.AddItem.route
                        )
                    }
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("heroId") { type = NavType.StringType },
                    navArgument("heroName") { type = NavType.StringType }
                )
            ) {
                val id = it.arguments?.getString("heroId") ?: ""
                val name = it.arguments?.getString("heroName") ?: ""
                mTitleBar = name
                DetailScreen(heroId = id)
            }

            composable(route = Screen.Profile.route) {
                mTitleBar = "About"
                ProfileScreen(modifier = modifier)
            }
            composable(route = Screen.AddItem.route) {
                mTitleBar = "Add Item Vehicle"
                AddItemScreen(modifier = modifier, navHostController = navHostController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    HelloJetpackComposeTheme {
        JetHeroesApp()
    }
}

fun showToast(context: Context, name: String) {
    Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun HeroListItemPreview() {
    HelloJetpackComposeTheme {
        HeroListItem(
            name = "Andi",
            licenseNumber = "",
            dateEntering = "",
            tonnageWeight = "",
            photoUrl = "",
            selectedHero = { s -> }
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetHeroesAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.vehicle_app), color = Color.White) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}




