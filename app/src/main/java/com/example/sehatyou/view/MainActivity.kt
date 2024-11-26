package com.example.sehatyou.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.ui.theme.SehatYouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SehatYouTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomePage(navController) }
                    composable("setting") { SettingsPage(navController) }
                    composable("profile") { ProfilePage(navController) }
                    composable("personalize") { PersonalizePage(navController) }
                    composable("suggest") { SuggestPage(navController) }
                    composable("register") { RegisterPage(navController) }
                    composable("login") { LoginPage(navController) }
                    composable("smartwatch") {SmartWatchPage(navController)  }
                }
            }
        }
    }
}