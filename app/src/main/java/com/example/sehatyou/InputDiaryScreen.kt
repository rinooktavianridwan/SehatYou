package com.example.sehatyou

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class) // Tandai API Material 3 yang eksperimental
@Composable
fun InputDiaryScreen(onSaveClick: () -> Unit, onCancelClick: () -> Unit, onBackClick: () -> Unit) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header Row (Diari + Back Icon)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Diari",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF3E0028),
            )
//            IconButton(onClick = { onBackClick() }) {
            Image(
//                    imageVector = Icons.Default.ArrowBack, // Gunakan ikon bawaan
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back",
                modifier = Modifier.size(40.dp)
//                    tint = Color(0xFF3E0028)
            )
//            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TextField untuk Judul
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            placeholder = { Text("Judul", color = Color(0xFF3E0028)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFFE0B2),
                focusedTextColor = Color(0xFF3E0028),  // Warna teks saat fokus
                unfocusedTextColor = Color(0xFF3E0028), // Warna teks saat tidak fokus
                cursorColor = Color(0xFF3E0028),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE0B2), RoundedCornerShape(7.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // TextField untuk Deskripsi
        TextField(
            value = description.value,
            onValueChange = { description.value = it },
            placeholder = { Text("Deskripsi", color = Color(0xFF3E0028)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFFE0B2),
                focusedTextColor = Color(0xFF3E0028),  // Warna teks saat fokus
                unfocusedTextColor = Color(0xFF3E0028), // Warna teks saat tidak fokus
                cursorColor = Color(0xFF3E0028),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color(0xFFFFE0B2), RoundedCornerShape(7.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pilih Kategori
        Text(text = "Pilih Kategori:", color = Color(0xFF3E0028))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_mood),
                contentDescription = "Mood",
//                modifier = Modifier.fillMaxWidth()
                modifier = Modifier
                    .width(400.dp)
                    .height(65.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tombol Batal dan Simpan
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onCancelClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E0028))
            ) { Text("Batal", color = Color.White) }

            Button(
                onClick = { onSaveClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E0028))
            ) { Text("Simpan", color = Color.White) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputDiaryScreen() {
    InputDiaryScreen(
        onSaveClick = {},
        onCancelClick = {},
        onBackClick = {}
    )
}