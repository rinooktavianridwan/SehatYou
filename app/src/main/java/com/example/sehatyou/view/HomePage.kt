package com.example.sehatyou.view

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.SehatYouRoomModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun HomePage(
    navController: NavController = rememberNavController(),
    viewModel: SehatYouRoomModel,
    initialData: HealthData?
) {

    // Mutable state untuk data yang sedang ditampilkan
    val displayedData = remember { mutableStateOf(initialData) }

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
                modifier = Modifier.fillMaxWidth(),
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
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
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
        }
    }
}
