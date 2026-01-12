package com.example.runique

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationRoot(
     navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = NavigationGraphs.Auth
    ){
        auth(navHostController)
        run(navHostController)
    }
}