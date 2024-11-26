package com.example.sehatyou.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R


@Composable
fun HomePage(navController: NavController = rememberNavController()) {
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
                onClick = {navController.navigate("setting") },
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActivityCard(
                title = "Tidur",
                subtitle = "7j 8m",
                icon = R.drawable.tidur,
                backgroundColor = colorResource(id = R.color.bluedark) // Warna biru untuk Tidur
            )
            ActivityCard(
                title = "Olahraga",
                subtitle = "3j 12m",
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
                subtitle = "89 bpm",
                icon = R.drawable.detakjantung,
                backgroundColor = Color.White
            )
            ActivityCard(
                title = "Kalori Terbakar",
                subtitle = "100 kkal",
                icon = R.drawable.lari,
                backgroundColor = Color.White
            )
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
                Column(
                ) {
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
                        onClick = { /*TODO*/ },
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
                        Text(text = "Lihat Semua", color = colorResource(id = R.color.purple3C1732))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SuggestionCard(
                        title = "Membaca Buku",
                        deskripsi = "Luangkan waktu untuk membaca buku yang sudah lama ingin kamu baca. Membaca bisa memberikan wawasan baru.",
                        tanggal = "23 Oktober 2024",
                        waktu = "08:15 a.m.",
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionCard(title: String, deskripsi: String, tanggal: String, waktu: String) {
    var isStarred by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(bottom = 0.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .background(colorResource(id = R.color.DDE7FF), CircleShape)
                ) {
                    // Bintang untuk border
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                    )
                    //Bintang
                    IconButton(
                        onClick = { isStarred = !isStarred },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite",
                            tint = if (isStarred) Color.Yellow else colorResource(id = R.color.DDE7FF),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

            }
            Text(
                text = deskripsi,
                fontSize = 14.sp,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp))
                .padding(0.dp)
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = tanggal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.Gray)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = waktu,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun ActivityCard(title: String, subtitle: String, icon: Int, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .size(160.dp, 80.dp)
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(8.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.width(60.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.F7B087)
                )
                Text(text = subtitle, fontSize = 12.sp, color = colorResource(id = R.color.F7B087))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage()
}