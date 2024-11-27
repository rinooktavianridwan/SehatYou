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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
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
import com.example.sehatyou.model.SehatYouModel
import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SuggestPage(navController: NavController = rememberNavController(), viewModel: SehatYouModel) {
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

    val savedSuggestions by viewModel.getAllTasks.collectAsState(initial = emptyList())

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

    // Fungsi untuk mendapatkan saran
    fun fetchSuggestion() {
        coroutineScope.launch {
            isLoading.value = true
            try {
                val descriptionMessages =
                    listOf(Message("user", "Kamu adalah SehatBot, sebuah bot yang dibuat untuk aplikasi SehatYou. SehatYou adalah aplikasi mobile yang mengintegrasikan data kesehatan fisik dari smartwatch dan data mental well-being dari catatan harian (diary). Tujuan kamu adalah memberikan saran kegiatan yang relevan dan bermanfaat kepada user berdasarkan data tersebut.\n" +
                            "\n" +
                            "Berikut adalah aturan yang harus kamu ikuti:\n" +
                            "1. Kamu akan diberikan informasi berupa diary user dan data smartwatch user (jika tersedia).\n" +
                            "2. Jika data smartwatch tidak tersedia, kamu tetap harus memberikan saran yang relevan berdasarkan diary saja.\n" +
                            "3. Output harus berupa saran kegiatan singkat dalam *bahasa Indonesia, dengan panjang maksimal **25 kata*.\n" +
                            "4. Output harus berupa kalimat langsung yang praktis dan actionable (dapat langsung dilakukan oleh user).\n" +
                            "5. Jika ini bukan permintaan pertama, sistem akan menyertakan saran sebelumnya agar kamu dapat memberikan saran baru yang melengkapi saran sebelumnya tanpa mengulanginya.\n" +
                            "6. Pertimbangkan konteks waktu (pagi, siang, malam) dan mood user yang tergambar dalam diary untuk membuat saran lebih personal.\n" +
                            "7. Jika diary menunjukkan suasana hati negatif (stres, sedih), utamakan saran untuk manajemen stres atau aktivitas relaksasi.\n" +
                            "8. Jika diary menunjukkan suasana hati positif (bahagia, produktif), berikan saran untuk mempertahankan atau meningkatkan suasana tersebut.\n" +
                            "\n" +
                            "Tugasmu adalah memberikan saran kegiatan yang relevan dan terarah untuk user.\n" +
                            "\n" +
                            "Ini adalah user dengan nama Vgte, dan ini adalah diary milik nya\n" +
                            "\n" +
                            "19/10/2024\n" +
                            "Pagi ini aku bangun lebih awal dari biasanya. Alarm berbunyi pukul 5 pagi, dan aku langsung bersiap. Ransel sudah kusiapkan sejak semalam—cukup ringan karena aku tak ingin ribet selama perjalanan. Setelah sarapan sederhana dengan nasi goreng dan teh manis buatan ibu, aku pamit. Rasanya campur aduk: senang, bersemangat, tapi juga sedikit gugup. Ini pertama kalinya aku ke Jepang, dan aku tak sabar melihat Kota Hakone yang terkenal dengan pemandangan Gunung Fuji-nya.\n" +
                            "\n" +
                            "Di bandara, proses check-in lancar, dan aku berhasil menemukan gate keberangkatanku tanpa drama. Penerbangan berlangsung sekitar 7 jam. Di pesawat, aku menghabiskan waktu dengan menonton film dan sesekali melihat awan putih dari jendela—indah sekali, seperti kapas.\n" +
                            "\n" +
                            "Aku mendarat di Bandara Haneda pada malam hari. Setelah melewati imigrasi dan mengambil bagasi, aku langsung mencari transportasi menuju Hakone. Dari Tokyo, aku naik kereta ekspres yang cukup cepat. Sampai di Hakone, udara dingin langsung menyambutku. Malam di sini terasa tenang, hanya suara angin dan sedikit gemericik air sungai di kejauhan. Aku check-in di sebuah ryokan (penginapan tradisional Jepang) yang hangat dan nyaman. Tatami di lantai dan futon di kamar membuatku merasa seperti sedang dalam film Jepang klasik.\n" +
                            "\n" +
                            "Setelah makan malam berupa bento khas Jepang yang lezat, aku tak banyak melakukan aktivitas. Lelah perjalanan membuatku memilih langsung tidur. Besok pagi, petualangan di Hakone dimulai!\n" +
                            "\n" +
                            "20/10/2024\n" +
                            "Pagi-pagi aku sudah bangun, jam di dinding menunjukkan pukul 6. Udara sejuk menyegarkan tubuh, dan pemandangan di luar jendela sungguh memanjakan mata. Gunung dan hutan yang hijau diselimuti kabut tipis. Setelah sarapan tradisional Jepang—ikan bakar, miso soup, nasi, dan teh hijau—aku memulai eksplorasi.\n" +
                            "\n" +
                            "Destinasi pertama adalah Hakone Open-Air Museum. Tempat ini penuh dengan patung-patung artistik yang dipajang di taman terbuka. Rasanya unik melihat seni modern berlatar belakang pegunungan. Tak jauh dari sana, aku juga mampir ke Museum Pola yang memamerkan koleksi seni dari berbagai belahan dunia.\n" +
                            "\n" +
                            "Setelah puas dengan museum, aku naik Hakone Ropeway untuk melihat pemandangan spektakuler. Di tengah perjalanan, aku beruntung karena cuaca cerah dan Gunung Fuji tampak dengan jelas. Aku mengambil beberapa foto, meski rasanya mata ini lebih puas memandang langsung daripada melalui lensa kamera.\n" +
                            "\n" +
                            "Di siang hari, aku singgah di Owakudani, sebuah kawasan vulkanik aktif. Asap sulfur menyelimuti udara, dan aku mencoba telur hitam khas daerah ini, yang katanya bisa memperpanjang umur. Rasanya seperti telur biasa, tapi pengalaman memakannya di tempat seperti ini membuatnya terasa lebih istimewa.\n" +
                            "\n" +
                            "Sore harinya, aku berjalan-jalan santai di tepi Danau Ashi. Air danau yang tenang, dengan kapal wisata berbentuk seperti kapal bajak laut yang melintas, memberikan suasana damai. Aku naik salah satu kapal untuk menikmati pemandangan dari tengah danau, dan itu menjadi salah satu momen favoritku hari ini.\n" +
                            "\n" +
                            "Malam harinya, aku kembali ke ryokan. Sebelum tidur, aku mencoba onsen (pemandian air panas) di penginapan. Suhu air yang hangat membuat tubuhku rileks setelah seharian berjalan kaki. Di bawah langit malam yang penuh bintang, aku berjanji akan kembali lagi ke Hakone suatu hari nanti.\n" +
                            "\n" +
                            "Sekarang 21/10/2024 pukul 6:00, berikan saran yang relevan untuk Vgte sekarang!\n" +
                            "\n" +
                            "(data smartwatch kosong/tidak ada karena user tidak menyambungkan smartwatch)"))
                val descriptionRequest = ChatRequest("llama-3.1-70b-versatile", descriptionMessages)
                val descriptionResponse = withContext(Dispatchers.IO) {
                    GroqApiClient.api.getChatResponse(descriptionRequest)
                }

                val descriptionText = descriptionResponse?.choices?.get(0)?.message?.content
                    ?: "Deskripsi tidak tersedia"

                val titleMessages = listOf(
                    Message(
                        "user",
                        "\"$descriptionText\"\nBerdasarkan saran yang kamu berikan di bawah ini, aku ingin kamu menjadikannya judul kegiatan untuk user. Judul ini harus terdiri dari maksimal 3 kata dalam bahasa Indonesia dan relevan dengan inti saran. Jangan tambahkan kata lain. "
                    )
                )
                val titleRequest = ChatRequest("llama3-8b-8192", titleMessages)
                val titleResponse = withContext(Dispatchers.IO) {
                    GroqApiClient.api.getChatResponse(titleRequest)
                }

                val titleText = titleResponse?.choices?.get(0)?.message?.content
                    ?: "Judul tidak tersedia"

                // Simpan data ke Room
                val newSuggestion = SuggestEntity(
                    title = titleText,
                    descripsion = descriptionText,
                    favorite = false,
                    date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                    time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                )
                viewModel.addTask(newSuggestion)

                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    suggestion.value = descriptionText
                    title.value = titleText
                    suggestionDate.value = newSuggestion.date
                    suggestionTime.value = newSuggestion.time
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
                    Button(onClick = { fetchSuggestion() }) {
                        Text(text = "Dapatkan Saran")
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        items(savedSuggestions) { item ->
                            SuggestionCard(
                                title = item.title,
                                deskripsi = item.descripsion,
                                tanggal = item.date,
                                waktu = item.time,
                                onClick = { /* Bisa tambahkan popup untuk detail */ }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

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


