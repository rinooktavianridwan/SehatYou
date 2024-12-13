package com.example.sehatyou.view

import android.widget.Toast
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.GroqAI.ChatRequest
import com.example.sehatyou.GroqAI.GroqApiClient
import com.example.sehatyou.GroqAI.Message
import com.example.sehatyou.R
import com.example.sehatyou.model.SehatYouRoomModel
import com.example.sehatyou.model.SuggestEntity
import com.example.sehatyou.utils.SuggestionFetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.sehatyou.utils.getLocalDate
import com.example.sehatyou.utils.getLocalTime

@Composable
fun SuggestPage(navController: NavController = rememberNavController(), viewModel: SehatYouRoomModel) {
    var tanggal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    val context = LocalContext.current

    val isPopupVisible = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val savedSuggestions by viewModel.getAllTasks.collectAsState(initial = emptyList())

    val sortedSuggestions = savedSuggestions.sortedWith(
        compareByDescending<SuggestEntity> {
            it.favorite
        }.thenByDescending {
            LocalDateTime.of(it.getLocalDate(), it.getLocalTime())
        }
    )

    val selectedSuggestion = remember { mutableStateOf<SuggestEntity?>(null) }
    val isLoadingItem = remember { mutableStateOf(false) }
    val favoriteSuggestions = sortedSuggestions.filter { it.favorite }
    val otherSuggestions = sortedSuggestions.filter { !it.favorite }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5)).padding(top=32.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 30.dp, end = 0.dp, bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(235.dp))

                IconButton(
                    onClick = { navController.navigate("menu")},
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "menu",
                        modifier = Modifier.size(40.dp)
                    )

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .background(
                        colorResource(id = R.color.FFDEC5),
                        RoundedCornerShape(60.dp, 60.dp, 0.dp, 0.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tanggal.toString(), modifier = Modifier
                            .background(
                                colorResource(id = R.color.white),
                                RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp, 5.dp)
                    )

                    IconButton(
                        onClick = {
                            navController.navigate("home")
                        },
                        colors = IconButtonColors(
                            Color.White,
                            Color.Black,
                            Color.White,
                            Color.Black
                        ),
                        modifier = Modifier
                            .size(30.dp, 30.dp)
                            .shadow(
                                0.dp,
                                CircleShape,
                                ambientColor = Color.Black,
                                spotColor = Color.Black
                            ),
                    )
                    {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "back",
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .animateContentSize()
                    ) {
                        items(favoriteSuggestions) { item ->
                            SuggestionCard(
                                title = item.title,
                                deskripsi = item.descripsion,
                                tanggal = item.date,
                                waktu = item.time,
                                onClick = {
                                    selectedSuggestion.value = item
                                    isPopupVisible.value = true
                                },
                                isStarred = item.favorite,
                                onStarToggle = {
                                    viewModel.update(item.copy(favorite = !item.favorite))
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Menampilkan loading indicator (jika ada)
                        if (isLoadingItem.value) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .background(
                                            color = Color.LightGray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .height(60.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(color = Color.Gray)
                                }
                            }
                        }

                        // Menampilkan saran lainnya
                        items(otherSuggestions) { item ->
                            SuggestionCard(
                                title = item.title,
                                deskripsi = item.descripsion,
                                tanggal = item.date,
                                waktu = item.time,
                                onClick = {
                                    selectedSuggestion.value = item
                                    isPopupVisible.value = true
                                },
                                isStarred = item.favorite,
                                onStarToggle = {
                                    viewModel.update(item.copy(favorite = !item.favorite))
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isLoadingItem.value = true
                                try {
                                    val newSuggestion = SuggestionFetcher.fetchSuggestion()
                                    viewModel.add(newSuggestion)
                                } catch (e: Exception) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                                } finally {
                                    isLoadingItem.value = false
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 32.dp) // Menambahkan padding bawah
                    ) {
                        Text(text = "Dapatkan Saran")
                    }

                }
            }
        }
    }
    if (isPopupVisible.value && selectedSuggestion.value != null) {
        SuggestionDetailPopup(
            title = selectedSuggestion.value?.title ?: "",
            description = selectedSuggestion.value?.descripsion ?: "",
            date = selectedSuggestion.value?.date ?: "",
            isStarred = selectedSuggestion.value?.favorite ?: false,
            onDismiss = {
                isPopupVisible.value = false
                selectedSuggestion.value = null
            },
            onStarToggle = {
                selectedSuggestion.value?.let {
                    val updatedSuggestion = it.copy(favorite = !it.favorite)
                    viewModel.update(updatedSuggestion)
                    selectedSuggestion.value = updatedSuggestion
                }
            },
            onDelete = {
                selectedSuggestion.value?.let { viewModel.delete(it) }
                isPopupVisible.value = false
                selectedSuggestion.value = null
            }
        )
    }
}



