package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SehatYouRoomModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.sehatyou.utils.getLocalDate
import com.example.sehatyou.utils.getLocalTime

@Composable
fun DiaryPage(navController: NavController = rememberNavController(), viewModel: SehatYouRoomModel) {
    var searchText by remember { mutableStateOf("") }
    val diaryList by viewModel.searchResults.collectAsState(initial = emptyList())

    LaunchedEffect(searchText) {
        viewModel.updateSearchQuery(searchText)
    }

    val sortedDiary = diaryList.sortedWith(
        compareByDescending<DiaryEntity> {
            it.favorite
        }.thenByDescending {
            LocalDateTime.of(it.getLocalDate(), it.getLocalTime())
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Diari",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF3C1732)
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

            // Search Field
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFFFE0B2), shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = "Search",
                    tint = Color(0xFF3C1732),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Cari",
                                color = Color(0xFF3C1732),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of Diaries
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(sortedDiary) { diary ->
                    DiaryCard(
                        date = diary.date,
                        title = diary.title,
                        description = diary.description,
                        isFavorite = diary.favorite,
                        onEditClick = { navController.navigate("diary_input_edit/${diary.id}") },
                        onDeleteClick = { viewModel.deleteDiary(diary) },
                        onFavoriteClick = {
                            viewModel.updateDiary(
                                diary.copy(favorite = !diary.favorite) // Toggle favorite status
                            )
                        }
                    )
                }
            }
        }

        // Floating Action Button
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f) // Ensure button appears on top
        ) {
            IconButton(
                onClick = { navController.navigate("diary_input_edit") },
                modifier = Modifier
                    .size(100.dp) // Dynamic size for the button
                    .offset(x = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_plus),
                    contentDescription = "Add",
                    modifier = Modifier.size(100.dp) // Modify size here
                )
            }
        }
    }
}

