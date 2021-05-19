package com.composeinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composeinstagram.ui.screen.LoginScreen
import com.composeinstagram.ui.theme.ComposeInstagramTheme
import com.composeinstagram.viewmodel.MainViewModel
import com.composeinstagram.wrapper.CachedIGClientState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeInstagramTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SplashRoute
                ) {
                    composable(SplashRoute) {
                        SplashScreen(
                            navToMain = {
                                navController.navigate(MainRoute)
                            },
                            navToLogin = {
                                navController.navigate(LoginRoute) {
                                    popUpTo(SplashRoute) { inclusive = true }
                                }
                            })
                    }
                    composable(LoginRoute) {
                        LoginScreen {
                            navController.navigate(MainRoute)
                        }
                    }
                    composable(MainRoute) {
                        MainScreen()
                    }
                }

            }
        }
    }
}

@Composable
fun SplashScreen(
    navToMain: () -> Unit,
    navToLogin: () -> Unit,
    mViewModel: MainViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxSize()
    )

    if (mViewModel.cachedIGClientState == CachedIGClientState.Success)
        navToMain()
    else if (mViewModel.cachedIGClientState == CachedIGClientState.Fail)
        navToLogin()
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxSize()
    )
}


