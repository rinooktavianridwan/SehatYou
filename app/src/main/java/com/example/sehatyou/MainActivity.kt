package com.example.sehatyou

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.view.RegisterPage
import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SehatYouRoomModel
import com.example.sehatyou.roomdb.OfflineSehatYouRepository
import com.example.sehatyou.roomdb.SehatYouDatabase
import com.example.sehatyou.ui.theme.SehatYouTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.content.Context
import com.example.sehatyou.view.DiaryPage
import com.example.sehatyou.view.HomePage
import com.example.sehatyou.view.InputEditDiaryPage
import com.example.sehatyou.view.KetentuanLayananPage
import com.example.sehatyou.view.LandingPage
import com.example.sehatyou.view.LoginPage
import com.example.sehatyou.view.PersonalizePage
import com.example.sehatyou.view.ProfilePage
import com.example.sehatyou.view.SehatYouViewModelFactory
import com.example.sehatyou.view.SettingsPage
import com.example.sehatyou.view.SmartWatchPage
import com.example.sehatyou.view.SuggestPage
import com.example.sehatyou.view.SyaratPage
import com.opencsv.CSVReader
import java.io.InputStreamReader

// Data class untuk menampung data
data class HealthData(
    val detakJantung: Int,
    val langkahKaki: Int,
    val jamTidur: Double,
    val kaloriTerbakar: Int
)

// Variabel global untuk menyimpan data yang dipilih
var selectedRow: HealthData? = null

// Fungsi untuk membaca CSV
fun readCSV(context: Context): List<HealthData> {
    val healthDataList = mutableListOf<HealthData>()
    val inputStream = context.assets.open("dummy_health_data_200.csv")
    val reader = CSVReader(InputStreamReader(inputStream))

    // Skip header
    val rows = reader.readAll()
    for (i in 1 until rows.size) {
        val row = rows[i]
        healthDataList.add(
            HealthData(
                detakJantung = row[0].toInt(),
                langkahKaki = row[1].toInt(),
                jamTidur = row[2].toDouble(),
                kaloriTerbakar = row[3].toInt()
            )
        )
    }
    reader.close()
    return healthDataList
}

// Fungsi untuk memilih data secara acak di awal
fun selectRandomRow(dataList: List<HealthData>) {
    selectedRow = dataList.random()
}

// Fungsi untuk memperbarui data berdasarkan kondisi
fun updateSelectedRow(dataList: List<HealthData>) {
    selectedRow?.let { currentRow ->
        // Filter data berdasarkan kondisi
        val filteredData = dataList.filter { newRow ->
            newRow.langkahKaki > currentRow.langkahKaki &&
                    newRow.jamTidur > currentRow.jamTidur &&
                    newRow.kaloriTerbakar > currentRow.kaloriTerbakar
        }

        // Jika ada data yang memenuhi, pilih salah satu secara acak
        if (filteredData.isNotEmpty()) {
            selectedRow = filteredData.random()
        }
        // Jika tidak ada data yang memenuhi, data lama tetap digunakan
    } ?: run {
        // Jika selectedRow null (belum diinisialisasi), pilih data acak
        selectRandomRow(dataList)
    }
}


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SehatYouRoomModel
    var isanimation = false
    private lateinit var healthDataList: List<HealthData>

    @SuppressLint("UnrememberedMutableInteractionSource")
    override fun onCreate(savedInstanceState: Bundle?) {

        healthDataList = readCSV(applicationContext)
        selectRandomRow(healthDataList)
        val initialHealthData = selectedRow

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
        //NotificationScheduler.scheduleNotifications(this, 3)

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
                        composable("home") { HomePage(navController, viewModel, initialHealthData) }
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
