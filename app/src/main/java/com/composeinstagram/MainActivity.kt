package com.composeinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
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
                        SplashScreen(navController)
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
    navController: NavController,
    mViewModel: MainViewModel = hiltNavGraphViewModel()
) {
    LaunchedEffect(key1 = mViewModel.cachedIGClientState) {
        if (mViewModel.cachedIGClientState == CachedIGClientState.Success)
            navController.navigate(MainRoute)
        else if (mViewModel.cachedIGClientState == CachedIGClientState.Fail)
            navController.navigate(LoginRoute)
    }

    Box(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxSize()
    )
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxSize()
    )
}


