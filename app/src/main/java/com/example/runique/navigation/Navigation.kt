package com.example.runique.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.presentation.intro.IntroRoot
import com.example.auth.presentation.login.LoginRoot
import com.example.auth.presentation.register.RegisterRoot
import com.example.run.presentation.active_run.ActiveRunRoot
import com.example.run.presentation.run_overview.RunOverviewRoot

fun NavGraphBuilder.auth(navHostController: NavHostController) {
    navigation<NavigationGraphs.Auth>(
        startDestination = NavigationScreens.Intro,
    ) {
        composable<NavigationScreens.Intro> {
            IntroRoot(
                onSignUpClick = {
                    navHostController.navigate(NavigationScreens.Register) {
                        //top backstack is Intro and inclusive = true means remove Intro
                        //backstack will be Register
                        //if inclusive = false then backstack: Intro, Register
                        popUpTo(NavigationScreens.Intro) {
                            inclusive = true
                        }
                    }
                },
                onSignInClick = {
                    navHostController.navigate(NavigationScreens.LogIn) {
                        popUpTo(NavigationScreens.Intro) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavigationScreens.Register> {
            RegisterRoot(
                onSignInClick = {
                    navHostController.navigate(NavigationScreens.LogIn) {
                        //Register will be removed from backstack
                        //saveState = true and restoreState = true means whenever we leave Register screen save it's state
                        //and whenever we navigate to Register screen restore the old saved state
                        popUpTo(NavigationScreens.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navHostController.navigate(NavigationScreens.LogIn)
                },
            )
        }

        composable<NavigationScreens.LogIn> {
            LoginRoot(
                onSignUpClick = {
                    navHostController.navigate(NavigationScreens.Register) {
                        popUpTo(NavigationScreens.LogIn) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onLoginSuccess = {
                    navHostController.navigate(NavigationGraphs.Run) {
                        popUpTo(NavigationGraphs.Auth) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.run(navHostController: NavHostController) {
    navigation<NavigationGraphs.Run>(
        startDestination = NavigationScreens.Run
    ) {
        composable<NavigationScreens.Run> {
            RunOverviewRoot(
                onStartRunClick = {
                    navHostController.navigate(NavigationScreens.ActiveRun)
                }
            )
        }
        composable<NavigationScreens.ActiveRun> {
            ActiveRunRoot()
        }
    }
}