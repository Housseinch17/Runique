package com.example.runique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.core.presentation.designsystem.RuniqueTheme
import com.example.runique.navigation.NavigationRoot
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.state.value.isCheckingAuth
        }
        enableEdgeToEdge()
        setContent {
            RuniqueTheme {
                val navController = rememberNavController()
                val state by mainViewModel.state.collectAsStateWithLifecycle()
                if (!state.isCheckingAuth) {
                    NavigationRoot(
                        navHostController = navController,
                        isLoggedIn = state.isLoggedIn
                    )
                }
            }
        }
    }
}
