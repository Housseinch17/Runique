package com.example.runique.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationGraphs : NavigationScreens {
    @Serializable
    data object Auth : NavigationScreens

    @Serializable
    data object Run : NavigationScreens
}