package com.example.gigifai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

val LocalDarkMode = staticCompositionLocalOf<MutableState<Boolean>> { error("DarkMode not provided") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkModeState = remember { mutableStateOf(true) }

            val darkColorsPalette = darkColors(
                primary = androidx.compose.ui.graphics.Color(0xFFBB86FC),
                primaryVariant = androidx.compose.ui.graphics.Color(0xFF3700B3),
                secondary = androidx.compose.ui.graphics.Color(0xFF03DAC6)
            )
            val lightColorsPalette = lightColors(
                primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
                primaryVariant = androidx.compose.ui.graphics.Color(0xFF3700B3),
                secondary = androidx.compose.ui.graphics.Color(0xFF03DAC6)
            )

            val colors = if (darkModeState.value) darkColorsPalette else lightColorsPalette

            MaterialTheme(colors = colors) {
                androidx.compose.runtime.CompositionLocalProvider(LocalDarkMode provides darkModeState) {
                    GigifaiApp()
                }
            }
        }
    }
}

@Composable
fun GigifaiApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("song/{index}") { backStackEntry ->
            val idx = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            SongScreen(navController, startIndex = idx)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    val darkModeState = remember { mutableStateOf(true) }
    androidx.compose.runtime.CompositionLocalProvider(LocalDarkMode provides darkModeState) {
        GigifaiApp()
    }
}