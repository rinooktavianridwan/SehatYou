package com.example.sehatyou.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.sehatyou.GroqAI.ChatRequest
import com.example.sehatyou.GroqAI.GroqApiClient
import com.example.sehatyou.GroqAI.Message
import com.example.sehatyou.model.SuggestEntity
import com.example.sehatyou.roomdb.OfflineSehatYouRepository
import com.example.sehatyou.roomdb.SehatYouDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.sehatyou.data.HealthData
import com.example.sehatyou.model.UserEntity
import com.google.firebase.auth.FirebaseAuth

object SuggestionFetcher {

    private lateinit var repository: OfflineSehatYouRepository

    // Initialize the repository with application context
    fun initialize(context: Context) {
        val database = SehatYouDatabase.getDatabase(context)
        repository = OfflineSehatYouRepository(database.suggestDao(),database.diaryDao())
    }

    suspend fun fetchSuggestion(): SuggestEntity {
        val systemAI = """
            Kamu adalah SehatBot, sebuah bot yang dibuat untuk aplikasi SehatYou. SehatYou adalah aplikasi mobile yang mengintegrasikan data kesehatan fisik dari smartwatch dan data mental well-being dari catatan harian (diary). Tugas kamu adalah memberikan saran kegiatan yang relevan dan bermanfaat kepada user berdasarkan data tersebut.
            Berikut adalah aturan yang harus kamu ikuti:
            1. Kamu akan diberikan informasi berupa diary user dan/atau data smartwatch user. Jika hanya salah satu yang tersedia, gunakan data yang tersedia untuk memberikan saran. Jika keduanya tidak tersedia, berikan saran umum yang tetap relevan dan actionable.
            2. Output harus berupa saran kegiatan singkat dalam bahasa Indonesia, dengan panjang maksimal 25 kata, tetapi idealnya antara 20–30 kata untuk fleksibilitas.
            3. Output harus berupa kalimat langsung yang praktis dan actionable (dapat langsung dilakukan oleh user). Contoh output: "Luangkan waktu untuk membaca buku favorit sebelum tidur."
            4. Jangan tambahkan kata atau frasa pengantar seperti "Berdasarkan diary user" atau "Berikut saran kegiatan." Langsung berikan saran kegiatan.
            5. Jika ini bukan permintaan pertama, sistem akan menyertakan saran sebelumnya agar kamu dapat memberikan saran baru yang melengkapi saran sebelumnya tanpa mengulanginya.
            6. Pertimbangkan konteks berikut untuk personalisasi saran:
               - **Konteks waktu:** Saran pagi, siang, atau malam.
               - **Mood user:** Jika diary menunjukkan mood negatif (stres, sedih), fokuskan pada manajemen stres atau relaksasi. Jika mood positif (bahagia, produktif), fokuskan pada mempertahankan atau meningkatkan suasana hati tersebut.
               - **Data smartwatch:** Gunakan data fisik seperti langkah harian, detak jantung, atau kualitas tidur untuk memberikan saran yang lebih kontekstual.
            7. Jika ada konflik antara mood user dan data smartwatch (misalnya, mood stres tetapi aktivitas tinggi), prioritaskan mood untuk memberikan saran yang lebih relevan secara emosional.
            8. Berikan saran yang beragam dan tidak berulang dalam sesi yang sama. Jika diperlukan, buat saran bertahap yang saling melengkapi.
            9. Jika tidak ada data (diary maupun smartwatch), berikan saran umum yang relevan untuk kesejahteraan mental dan fisik.
            
            Tujuanmu adalah memberikan saran kegiatan yang personal, relevan, dan dapat meningkatkan kesejahteraan fisik maupun mental user.
        """.trimIndent()

        return withContext(Dispatchers.IO) {
            try {
                // Retrieve data
                HealthData.updateSelectedRow()
                val smartwatchData = HealthData.getSelectedRow()

                // Retrieve the 7 newest diaries
                val diaries = repository.getLatestDiaries(7)
                val diaryText = diaries.joinToString("\n") { "${it.category},${it.date}:${it.time}\n${it.description}" }

                // Retrieve the 10 newest suggestions
                val suggestions = repository.getLatestSuggestions(10)
                val suggestionText = suggestions.joinToString("\n") { "${it.date}:${it.time}\n${it.descripsion}" }

                val currentDateTime = LocalDateTime.now()
                val formattedDate = currentDateTime.format(
                    DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
                )
                val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale("id", "ID")))

                var messageAI = "\"$systemAI\"\n"

                if (diaries.isNotEmpty()) {
                    messageAI +=
                        "Ini adalah data suasana mood, tanggal, jam, dan isi diary milik user:\n" +
                        "$diaryText\n"
                } else {
                    messageAI += "(Tidak ada diary yang dituliskan)\n"
                }

                if (suggestions.isNotEmpty()) {
                    messageAI +=
                        "Berikut adalah saran yang kamu berikan sebelumnya:\n" +
                        "$suggestionText\n"
                } else {
                    messageAI += "(Tidak ada saran yang sebelumnya diberikan)\n"
                }

                if (smartwatchData != null) {
                    messageAI +=
                        "Ini adalah data smartwatch dari user:\n" +
                        "Detak jantung=${smartwatchData.detakJantung}\n" +
                        "Langkah kaki=${smartwatchData.langkahKaki}\n" +
                        "Jam tidur=${smartwatchData.jamTidur}\n" +
                        "Kalori terbakar=${smartwatchData.kaloriTerbakar}\n"
                } else {
                    messageAI += "(data smartwatch kosong/tidak ada karena user tidak menyambungkan smartwatch)"
                }

                messageAI += "Sekarang $formattedDate pukul $formattedTime, berikan saran yang relevan sekarang untuk user\n"
                Log.d("ai msg", messageAI)

                val descriptionMessages = listOf(
                    Message(
                        role = "user",
                        content = messageAI
                    )
                )
                val descriptionRequest = ChatRequest("llama-3.1-70b-versatile", descriptionMessages)
                val descriptionResponse = GroqApiClient.api.getChatResponse(descriptionRequest)
                val descriptionText = descriptionResponse?.choices?.get(0)?.message?.content
                    ?: "Deskripsi tidak tersedia"

                val titleMessages = listOf(
                    Message(
                        "user",
                        "\"$systemAI\"\n" +
                                "Berdasarkan saran yang kamu berikan di bawah ini, aku ingin kamu menjadikannya judul kegiatan untuk user. Judul ini harus terdiri dari maksimal 3 kata dalam bahasa Indonesia dan relevan dengan inti saran. Jangan tambahkan kata lain.\n" +
                                " \"$descriptionText\"\n"
                    )
                )
                val titleRequest = ChatRequest("llama-3.1-70b-versatile", titleMessages)
                val titleResponse = GroqApiClient.api.getChatResponse(titleRequest)
                val titleText = titleResponse?.choices?.get(0)?.message?.content
                    ?: "Judul tidak tersedia"

                SuggestEntity(
                    title = titleText,
                    descripsion = descriptionText,
                    date = formattedDate,
                    time = formattedTime,
                    favorite = false
                )

            } catch (e: Exception) {
                throw e
            }
        }
    }
}