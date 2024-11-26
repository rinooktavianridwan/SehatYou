package com.example.sehatyou.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SmartWatchPage(navController: NavController = rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bagian header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SmartWatch",
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
            // Tombol kembali
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonColors(Color.White, Color.Black, Color.White, Color.Black),
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .padding(start = 0.dp, top = 15.dp, end = 20.dp, bottom = 0.dp)
                    .shadow(4.dp, CircleShape, ambientColor = Color.Black, spotColor = Color.Black),
            )
            {
                Icon(imageVector  = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }
        }

        // Bagian daftar smartwatch
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(colorResource(id = R.color.FFDEC5))
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Daftar smartwatch
            SmartwatchContainer(
                modifier = Modifier
                    .padding(16.dp)
            )
            SmartwatchContainer(
                modifier = Modifier
                    .padding(16.dp)
            )

            // Tombol Pindai Perangkat Baru
            Button(
                onClick = {
                    // Aksi memindai perangkat baru
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp)
            ) {
                Text(text = "Pindai Perangkat Baru")
            }
        }
    }
}

@Composable
fun SmartwatchContainer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color(0xFFFFFFFF))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Nama Produk", color = Color.Black)
                Text("Merk", color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmartwatchContainerPrev() {
    SmartwatchContainer(
        modifier = Modifier
            .padding(16.dp)
    )
}

@Preview
@Composable
private fun PrevSmartwatchpage() {
    SmartWatchPage()
}
