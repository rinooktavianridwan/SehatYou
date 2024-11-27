package com.example.sehatyou.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.model.SehatYouModel
import com.example.sehatyou.roomdb.OfflineSehatYouRepository
import com.example.sehatyou.roomdb.SehatYouDatabase
import com.example.sehatyou.ui.theme.SehatYouTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SehatYouModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi ViewModel secara manual
        val sehatYouRepository = OfflineSehatYouRepository(
            SehatYouDatabase.getDatabase(applicationContext).suggestDao()
        )
        viewModel = ViewModelProvider(
            this,
            SehatYouViewModelFactory(sehatYouRepository)
        )[SehatYouModel::class.java]
        enableEdgeToEdge()
        setContent {
            SehatYouTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomePage(navController,viewModel) }
                    composable("setting") { SettingsPage(navController) }
                    composable("profile") { ProfilePage(navController) }
                    composable("personalize") { PersonalizePage(navController) }
                    composable("suggest") { SuggestPage(navController,viewModel) }
                    composable("register") { RegisterPage(navController) }
                    composable("login") { LoginPage(navController) }
                    composable("smartwatch") {SmartWatchPage(navController)  }
                    composable("diary") { EditDiaryPage(navController) }
                    composable("add_diary") { InputDiaryPage(navController) }
                }
            }
        }
    }
}