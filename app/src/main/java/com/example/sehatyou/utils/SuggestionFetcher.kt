package com.example.sehatyou.utils

import android.content.Context
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

object SuggestionFetcher {

    private lateinit var repository: OfflineSehatYouRepository

    // Initialize the repository with application context
    fun initialize(context: Context) {
        val database = SehatYouDatabase.getDatabase(context)
        repository = OfflineSehatYouRepository(database.suggestDao(),database.diaryDao())
    }

    suspend fun fetchSuggestion(): SuggestEntity {
        val systemAI = """
            Kamu adalah SehatBot, sebuah bot yang dibuat untuk aplikasi SehatYou. SehatYou adalah aplikasi mobile yang mengintegrasikan data kesehatan fisik dari smartwatch dan data mental well-being dari catatan harian (diary). Tujuan kamu adalah memberikan saran kegiatan yang relevan dan bermanfaat kepada user berdasarkan data tersebut.
            
            Berikut adalah aturan yang harus kamu ikuti:
            1. Kamu akan diberikan informasi berupa diary user dan data smartwatch user (jika tersedia).
            2. Jika data smartwatch tidak tersedia, kamu tetap harus memberikan saran yang relevan berdasarkan diary saja.
            3. Output harus berupa saran kegiatan singkat dalam bahasa Indonesia, dengan panjang maksimal 25 kata.
            4. Output harus berupa kalimat langsung yang praktis dan actionable (dapat langsung dilakukan oleh user).
            5. Jika ini bukan permintaan pertama, sistem akan menyertakan saran sebelumnya agar kamu dapat memberikan saran baru yang melengkapi saran sebelumnya tanpa mengulanginya.
            6. Pertimbangkan konteks waktu (pagi, siang, malam) dan mood user yang tergambar dalam diary untuk membuat saran lebih personal.
            7. Jika diary menunjukkan suasana hati negatif (stres, sedih), utamakan saran untuk manajemen stres atau aktivitas relaksasi.
            8. Jika diary menunjukkan suasana hati positif (bahagia, produktif), berikan saran untuk mempertahankan atau meningkatkan suasana tersebut.
            
            Tugasmu adalah memberikan saran kegiatan yang relevan dan terarah untuk user.
        """.trimIndent()

        return withContext(Dispatchers.IO) {
            try {
                // Retrieve the 7 newest diaries
                val diaries = repository.getLatestDiaries(7)
                val diaryText = diaries.joinToString("\n") { "${it.date}\n${it.description}" }

                // Retrieve the 10 newest suggestions
                val suggestions = repository.getLatestSuggestions(10)
                val suggestionText = suggestions.joinToString("\n") { "${it.date}\n${it.descripsion}" }

                val currentDateTime = LocalDateTime.now()
                val formattedDate = currentDateTime.format(
                    DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
                )
                val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale("id", "ID")))

                val descriptionMessages = listOf(
                    Message(
                        "user",
                        "\"$systemAI\"\n" +
                                "Ini adalah user dengan nama Vgte, dan ini adalah diary miliknya:\n" +
                                "\n$diaryText\n" +
                                "\nBerikut adalah 10 saran sebelumnya:\n" +
                                "\n$suggestionText\n" +
                                "\nSekarang $formattedDate pukul $formattedTime, berikan saran yang relevan sekarang untuk user\n" +
                                "\n(data smartwatch kosong/tidak ada karena user tidak menyambungkan smartwatch)"
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