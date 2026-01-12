package com.example.runique.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationScreens {
    @Serializable
    data object Intro : NavigationScreens

    @Serializable
    data object Register : NavigationScreens

    @Serializable
    data object LogIn : NavigationScreens

    @Serializable
    data object Run: NavigationScreens
}