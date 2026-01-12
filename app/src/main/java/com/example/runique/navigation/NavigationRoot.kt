package com.example.runique.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationRoot(
    navHostController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navHostController,
        startDestination = if (isLoggedIn) NavigationGraphs.Run else NavigationGraphs.Auth
    ) {
        auth(navHostController)
        run(navHostController)
    }
}