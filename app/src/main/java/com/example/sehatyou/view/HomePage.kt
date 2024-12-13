package com.example.sehatyou.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.data.HealthData
import com.example.sehatyou.R
import com.example.sehatyou.model.SehatYouRoomModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@SuppressLint("RememberReturnType")
@Composable
fun HomePage(
    navController: NavController = rememberNavController(),
    viewModel: SehatYouRoomModel,
) {
    // Mutable state untuk data yang sedang ditampilkan
    val context = LocalContext.current
    remember { HealthData.initializeData(context) }
    val displayedData = remember { mutableStateOf(HealthData.getSelectedRow()) }

    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID")))
    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a", Locale("id", "ID")))
    val savedSuggestions by viewModel.getAllTasks.collectAsState(initial = emptyList())
    val sortedSuggestions = savedSuggestions.sortedWith(
        compareByDescending {
            LocalDateTime.parse(
                "${it.date} ${it.time}",
                DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale("id", "ID"))
            )
        }
    )
    val latestSuggestion = sortedSuggestions.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 32.dp, 32.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* TODO: Tambahkan aksi untuk ikon logo */ },
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    tint = Color.Unspecified
                )
            }
            IconButton(
                onClick = { navController.navigate("setting") },
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "menu"
                )
            }
        }

        Text(
            modifier = Modifier.padding(top = 64.dp),
            fontSize = 24.sp,
            text = "Jadi Orang"
        )
        Text(
            modifier = Modifier.padding(bottom = 64.dp),
            fontSize = 24.sp,
            text = "yang Lebih Baik"
        )

        // Menampilkan data dari `displayedData`
        displayedData.value?.let { data ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActivityCard(
                    title = "Tidur",
                    subtitle = "${data.jamTidur} jam",
                    icon = R.drawable.tidur,
                    backgroundColor = colorResource(id = R.color.bluedark)
                )
                ActivityCard(
                    title = "Olahraga",
                    subtitle = "${data.langkahKaki} langkah",
                    icon = R.drawable.olahraga,
                    backgroundColor = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActivityCard(
                    title = "Detak Jantung",
                    subtitle = "${data.detakJantung} bpm",
                    icon = R.drawable.detakjantung,
                    backgroundColor = Color.White
                )
                ActivityCard(
                    title = "Kalori Terbakar",
                    subtitle = "${data.kaloriTerbakar} kkal",
                    icon = R.drawable.lari,
                    backgroundColor = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(id = R.color.purple3C1732),
                    RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp)
                )
                .padding(0.dp, 40.dp, 0.dp, 0.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 64.dp, bottom = 24.dp, end = 40.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(
                        text = "Bagaimana Mood Anda Hari Ini?",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Beri tahu kami apa yang sedang terjadi",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(colorResource(id = R.color.F89154), CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("diary")
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                            .padding(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "diary arrow",
                            tint = Color.White
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(id = R.color.FFDEC5),
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saran",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.purple3C1732),
                            modifier = Modifier
                                .background(colorResource(id = R.color.F7B087), CircleShape)
                                .padding(24.dp, 4.dp)
                        )
                        Text(
                            modifier = Modifier.clickable(onClick = { navController.navigate("suggest") }),
                            text = "Lihat Semua",
                            color = colorResource(id = R.color.purple3C1732)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (latestSuggestion != null) {
                        // Jika ada data, tampilkan SuggestionCard dengan data terbaru
                        SuggestionCard(
                            title = latestSuggestion.title,
                            deskripsi = latestSuggestion.descripsion,
                            tanggal = latestSuggestion.date,
                            waktu = latestSuggestion.time,
                            isStarred = latestSuggestion.favorite,
                            onClick = { /* Navigasi atau aksi */ },
                            onStarToggle = { /* Toggle favorite */ }
                        )
                    } else {
                        SuggestionCard(
                            title = "Saran Belum Tersedia",
                            deskripsi = "Tambahkan saran sendiri pada halaman Saran",
                            tanggal = currentDate,
                            waktu = currentTime,
                            isStarred = false,
                            onClick = { /* Navigasi atau aksi */ },
                            onStarToggle = {}
                        )
                    }
                }
            }
        }

    }
}
