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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.GroqAI.ChatRequest
import com.example.sehatyou.GroqAI.GroqApiClient
import com.example.sehatyou.GroqAI.Message
import com.example.sehatyou.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SuggestPage(navController: NavController = rememberNavController()) {
    var tanggal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    val suggestion = remember { mutableStateOf("Menunggu saran...") }
    val suggestionDate = remember { mutableStateOf("") }
    val suggestionTime = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    // Mendapatkan CoroutineScope untuk peluncuran coroutine
    val coroutineScope = rememberCoroutineScope()

    // Menampilkan loading indicator jika sedang memuat data
    if (isLoading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }

    // Fungsi untuk mengambil saran dari API
    fun fetchSuggestion() {
        coroutineScope.launch {
            isLoading.value = true
            val messages = listOf(Message("user", "Halo, Bagaimana kabarmu?"))
            val request = ChatRequest("llama3-8b-8192", messages)

            try {
                // Operasi API menggunakan fungsi `suspend` tanpa callback
                val response = withContext(Dispatchers.IO) {
                    GroqApiClient.api.getChatResponse(request)
                }

                // Update UI di thread utama
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    if (response != null) {
                        suggestion.value = response.choices?.get(0)?.message?.content ?: "Saran tidak tersedia"
                        suggestionDate.value = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                        suggestionTime.value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                    } else {
                        suggestion.value = "Gagal memuat saran."
                    }
                }
            } catch (e: Exception) {
                // Tangani error
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    suggestion.value = "Kesalahan: ${e.message}"
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5))
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "menu",
                        modifier = Modifier.size(55.dp)
                    )

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .background(
                        colorResource(id = R.color.FFDEC5),
                        RoundedCornerShape(60.dp, 60.dp, 0.dp, 0.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 50.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    colorResource(id = R.color.white),
                                    RoundedCornerShape(20.dp)
                                )
                        )
                        { Text(text = tanggal.toString(), Modifier.padding(10.dp, 5.dp)) }

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
                    Spacer(modifier = Modifier.height(16.dp))
                    SuggestionCard(
                        title = "Saran Aktivitas",
                        deskripsi = suggestion.value,
                        tanggal = suggestionDate.value,
                        waktu = suggestionTime.value,
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { fetchSuggestion() }) {
                        Text(text = "Dapatkan Saran")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchBoxPreview() {
    SuggestPage()
}