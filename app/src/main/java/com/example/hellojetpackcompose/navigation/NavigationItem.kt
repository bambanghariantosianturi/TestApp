package com.example.hellojetpackcompose.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Alo-BambangHariantoSianturi on 26/10/23.
 */
data class NavigationItem (
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)