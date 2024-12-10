package com.example.sehatyou.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SehatYouRoomModel
import com.example.sehatyou.roomdb.OfflineSehatYouRepository
import com.example.sehatyou.roomdb.SehatYouDatabase
import com.example.sehatyou.ui.theme.SehatYouTheme
import com.example.sehatyou.utils.NotificationScheduler
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SehatYouRoomModel
    var isanimation = false

    @SuppressLint("UnrememberedMutableInteractionSource")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val iconView = splashScreenView.iconView as? ImageView
            if (iconView != null) {
                iconView.setImageResource(R.drawable.splash)
                val slideAnimation = ObjectAnimator.ofFloat(iconView, "translationY", -300f, 0f)
                slideAnimation.duration = 800
                val bounce1 = ObjectAnimator.ofFloat(iconView, "translationY", 0f, -100f)
                bounce1.duration = 500
                val bounceBack1 = ObjectAnimator.ofFloat(iconView, "translationY", -100f, 0f)
                bounceBack1.duration = 500
                val bounce2 = ObjectAnimator.ofFloat(iconView, "translationY", 0f, -50f)
                bounce2.duration = 400
                val bounceBack2 = ObjectAnimator.ofFloat(iconView, "translationY", -50f, 0f)
                bounceBack2.duration = 400
                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(
                    slideAnimation,
                    bounce1,
                    bounceBack1,
                    bounce2,
                    bounceBack2
                )
                animatorSet.doOnEnd {
                    iconView.postDelayed({
                        isanimation = true
                        splashScreenView.remove()
                    }, 1000)
                }
                animatorSet.start()
            } else {
                isanimation = true
                splashScreenView.remove()
            }
        }
        createNotificationChannel()
        NotificationScheduler.scheduleNotifications(this, 288)

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
                    NavHost(navController = navController, startDestination = "landing") {
                        composable("home") { HomePage(navController, viewModel) }
                        composable("setting") { SettingsPage(navController) }
                        composable("profile") { ProfilePage(navController) }
                        composable("personalize") { PersonalizePage(navController) }
                        composable("suggest") { SuggestPage(navController, viewModel) }
                        composable("register") { RegisterPage(navController) }
                        composable("login") { LoginPage(navController) }
                        composable("smartwatch") { SmartWatchPage(navController) }
                        composable("diary") { DiaryPage(navController, viewModel) }
                        composable("terms") { SyaratPage(navController) }
                        composable("terms_and_conditions") { KetentuanLayananPage(navController) }

                        composable("diary_input_edit") {
                            val diary = DiaryEntity(
                                title = "",
                                description = "",
                                category = "Neutral",
                                date = LocalDate.now()
                                    .format(DateTimeFormatter.ofPattern("DD MM YYYY")),
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
                        composable("landing") { LandingPage(navController) }

                        // Menambahkan route untuk halaman syarat dan ketentuan
                        composable("terms") {
                            SyaratPage(navController) // Halaman Syarat
                            navController.navigate("SyaratPage")
                        }
                        composable("terms_and_conditions") {
                            KetentuanLayananPage(navController) // Halaman Ketentuan Layanan
                        }
                    }
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Suggest Notif"
            val descriptionText = "Notifikasi untuk saran"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
