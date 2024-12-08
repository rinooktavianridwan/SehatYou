package com.example.sehatyou.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.RegisterPage
import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SehatYouRoomModel
import com.example.sehatyou.roomdb.OfflineSehatYouRepository
import com.example.sehatyou.roomdb.SehatYouDatabase
import com.example.sehatyou.ui.theme.SehatYouTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SehatYouRoomModel
    @SuppressLint("UnrememberedMutableInteractionSource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi ViewModel secara manual
        val sehatYouRepository = OfflineSehatYouRepository(
            SehatYouDatabase.getDatabase(applicationContext).suggestDao(),
            SehatYouDatabase.getDatabase(applicationContext).diaryDao()
        )
        viewModel = ViewModelProvider(
            this,
            SehatYouViewModelFactory(sehatYouRepository)
        )[SehatYouRoomModel::class.java]
        enableEdgeToEdge()
        setContent {
            SehatYouTheme {
                val navController = rememberNavController()
                val focusManager = LocalFocusManager.current // Untuk mengelola fokus

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            focusManager.clearFocus() // Hapus fokus saat klik di luar area input
                        }
                ) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomePage(navController, viewModel) }
                        composable("setting") { SettingsPage(navController) }
                        composable("profile") { ProfilePage(navController) }
                        composable("personalize") { PersonalizePage(navController) }
                        composable("suggest") { SuggestPage(navController, viewModel) }
                        composable("register") { RegisterPage(navController) }
                        composable("login") { LoginPage(navController) }
                        composable("smartwatch") { SmartWatchPage(navController) }
                        composable("diary") { DiaryPage(navController, viewModel) }
                        composable("diary_input_edit") {
                            val diary = DiaryEntity(
                                title = "",
                                description = "",
                                category = "Neutral",
                                date = LocalDate.now()
                                    .format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                                time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                            )
                            InputEditDiaryPage(
                                navController = navController,
                                viewModel = viewModel,
                                diary = diary
                            )
                        }
                        composable("diary_input_edit/{diaryId}") { backStackEntry ->
                            val diaryId =
                                backStackEntry.arguments?.getString("diaryId")?.toIntOrNull()
                            val diaryLiveData =
                                diaryId?.let { viewModel.getDiaryById(it).observeAsState() }
                            val diary = diaryLiveData?.value
                                ?: return@composable // Jika `diaryId` tidak valid, keluar dari composable
                            InputEditDiaryPage(
                                navController = navController,
                                viewModel = viewModel,
                                diary = diary
                            )
                        }
                    }
                }
            }
        }
    }
}