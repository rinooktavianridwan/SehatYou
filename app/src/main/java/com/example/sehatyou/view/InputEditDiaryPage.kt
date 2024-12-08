package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SehatYouRoomModel


@Composable
fun InputEditDiaryPage(
    navController: NavController = rememberNavController(),
    viewModel: SehatYouRoomModel,
    diary: DiaryEntity
) {
    val title = remember { mutableStateOf(diary.title) }
    val description = remember { mutableStateOf(diary.description) }
    val selectedCategory = remember { mutableStateOf(diary.category) }
    val moodCategories = listOf("Angry", "Sad", "Neutral", "Happy", "Excited")
    val moodImages = listOf(
        R.drawable.icon_angry,
        R.drawable.icon_sad,
        R.drawable.icon_neutral,
        R.drawable.icon_happy,
        R.drawable.icon_excited
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        // Header Row (Diari + Back Icon)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (diary.id == 0) "Tambah Diari" else "Edit Diari",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF3E0028),
            )
            IconButton(onClick = { navController.popBackStack() }) { // Navigate back
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TextField untuk Judul
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            placeholder = { Text("Judul", color = Color(0xFF3E0028)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFE0B2),
                unfocusedContainerColor = Color(0xFFFFE0B2),
                focusedTextColor = Color(0xFF3E0028),
                unfocusedTextColor = Color(0xFF3E0028),
                cursorColor = Color(0xFF3E0028)
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
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFE0B2),
                unfocusedContainerColor = Color(0xFFFFE0B2),
                focusedTextColor = Color(0xFF3E0028),
                unfocusedTextColor = Color(0xFF3E0028),
                cursorColor = Color(0xFF3E0028)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color(0xFFFFE0B2), RoundedCornerShape(7.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            moodImages.forEachIndexed { index, imageRes ->
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            if (selectedCategory.value == moodCategories[index]) Color(0xFFFFE0B2) else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedCategory.value = moodCategories[index] }
                        .border(2.dp, Color(0xFF3E0028), RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Mood ${moodCategories[index]}",
                        modifier = Modifier
                            .size(64.dp) // Ukuran gambar di dalam kotak
                            .align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.popBackStack() }, // Cancel navigation
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E0028))
            ) { Text("Batal", color = Color.White) }

            Button(
                onClick = {
                    if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                        if (diary.id == 0) {
                            // Add new diary
                            viewModel.addDiary(
                                DiaryEntity(
                                    title = title.value,
                                    description = description.value,
                                    category = selectedCategory.value,
                                    favorite = false,
                                    date = diary.date,
                                    time = diary.time
                                )
                            )
                        } else {
                            // Update existing diary
                            viewModel.updateDiary(
                                diary.copy(
                                    title = title.value,
                                    description = description.value,
                                    category = selectedCategory.value
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E0028))
            ) { Text("Simpan", color = Color.White) }
        }
    }
}

