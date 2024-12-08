
package com.example.sehatyou.utils

import com.example.sehatyou.GroqAI.ChatRequest
import com.example.sehatyou.GroqAI.GroqApiClient
import com.example.sehatyou.GroqAI.Message
import com.example.sehatyou.model.SuggestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
            val descriptionMessages = listOf(
                Message(
                    "user",
                    "\"$systemAI\"\nIni adalah user dengan nama Vgte, dan ini adalah diary milik nya\n" +
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
                            "(data smartwatch kosong/tidak ada karena user tidak menyambungkan smartwatch)"
                )
            )
            val descriptionRequest = ChatRequest("llama-3.1-70b-versatile", descriptionMessages)
            val descriptionResponse = GroqApiClient.api.getChatResponse(descriptionRequest)
            val descriptionText = descriptionResponse?.choices?.get(0)?.message?.content
                ?: "Deskripsi tidak tersedia"

            val titleMessages = listOf(
                Message(
                    "user",
                    "\"$systemAI\"\nBerdasarkan saran yang kamu berikan di bawah ini, aku ingin kamu menjadikannya judul kegiatan untuk user. Judul ini harus terdiri dari maksimal 3 kata dalam bahasa Indonesia dan relevan dengan inti saran. Jangan tambahkan kata lain.\n \"$descriptionText\"\n"
                )
            )
            val titleRequest = ChatRequest("llama-3.1-70b-versatile", titleMessages)
            val titleResponse = GroqApiClient.api.getChatResponse(titleRequest)
            val titleText = titleResponse?.choices?.get(0)?.message?.content
                ?: "Judul tidak tersedia"

            SuggestEntity(
                title = titleText,
                descripsion = descriptionText,
                favorite = false,
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        } catch (e: Exception) {
            throw e
        }
    }
}