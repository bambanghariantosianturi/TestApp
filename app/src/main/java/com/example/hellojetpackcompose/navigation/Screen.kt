package com.example.hellojetpackcompose.navigation

/**
 * Created by Alo-BambangHariantoSianturi on 26/10/23.
 */
sealed class Screen(val route: String) {
    object Home: Screen("home/{query}") {
        fun createRoute(query: String) = "home/$query"
    }
    object Detail: Screen("detail/{heroId}/{heroName}") {
        fun createRoute(heroId: String, heroName: String) = "detail/$heroId/$heroName"
    }
    object Profile: Screen("profile")
    object AddItem: Screen("addItem")
}
