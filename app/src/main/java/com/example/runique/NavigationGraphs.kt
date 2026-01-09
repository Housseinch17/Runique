package com.example.runique

import kotlinx.serialization.Serializable

sealed interface NavigationGraphs : NavigationScreens {
    @Serializable
    data object Auth : NavigationScreens
}