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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val title = remember { mutableStateOf("Menunggu judul...") }

    // State untuk popup
    val isPopupVisible = remember { mutableStateOf(false) }
    val isStarred = remember { mutableStateOf(false) }

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

            try {
                // Permintaan pertama untuk mendapatkan deskripsi
                val descriptionMessages =
                    listOf(Message("user", "Halo, beri saya deskripsi aktivitas."))
                val descriptionRequest = ChatRequest("llama3-8b-8192", descriptionMessages)
                val descriptionResponse = withContext(Dispatchers.IO) {
                    GroqApiClient.api.getChatResponse(descriptionRequest)
                }

                val descriptionText =
                    descriptionResponse?.choices?.get(0)?.message?.content
                        ?: "Deskripsi tidak tersedia"

                // Permintaan kedua untuk mendapatkan judul berdasarkan deskripsi
                val titleMessages = listOf(
                    Message(
                        "user",
                        "Berdasarkan deskripsi ini: \"$descriptionText\", beri saya judul singkat."
                    )
                )
                val titleRequest = ChatRequest("llama3-8b-8192", titleMessages)
                val titleResponse = withContext(Dispatchers.IO) {
                    GroqApiClient.api.getChatResponse(titleRequest)
                }

                val titleText =
                    titleResponse?.choices?.get(0)?.message?.content ?: "Judul tidak tersedia"

                // Perbarui UI setelah mendapatkan data
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    suggestion.value = descriptionText
                    suggestionDate.value =
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                    suggestionTime.value =
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                    title.value = titleText
                }
            } catch (e: Exception) {
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
                    SuggestionCard(
                        title = title.value,
                        deskripsi = suggestion.value,
                        tanggal = suggestionDate.value,
                        waktu = suggestionTime.value,
                        onClick = { isPopupVisible.value = true } // Tampilkan popup saat diklik
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
    // Popup Detail
    if (isPopupVisible.value) {
        SuggestionDetailPopup(
            title = title.value,
            description = suggestion.value,
            date = suggestionDate.value,
            isStarred = isStarred.value,
            onDismiss = { isPopupVisible.value = false },
            onStarToggle = { isStarred.value = !isStarred.value }
        )
    }
}

@Composable
fun SuggestionDetailPopup(
    title: String,
    description: String,
    date: String,
    isStarred: Boolean,
    onDismiss: () -> Unit,
    onStarToggle: () -> Unit
) {
    var isStarred by remember { mutableStateOf(false) }
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth(),
        onDismissRequest = { onDismiss() },
        confirmButton = {},
        containerColor = Color.White,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(32.dp)
                            .background(colorResource(id = R.color.DDE7FF), CircleShape)
                    ) {
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
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .verticalScroll(rememberScrollState()) // Scrollable area
                        .background(Color.LightGray.copy(alpha = 0.2f)) // Optional background
                        .padding(8.dp)
                ) {
                    Text(
                        text = description,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Close")
                }
            }
        },
    )
}


@Preview
@Composable
fun SearchBoxPreview() {
    SuggestPage()
}