package com.example.sehatyou.data

import android.content.Context
import com.opencsv.CSVReader
import java.io.InputStreamReader

// Data class untuk menampung data
data class HealthData(
    val detakJantung: Int,
    val langkahKaki: Int,
    val jamTidur: Double,
    val kaloriTerbakar: Int
) {
    companion object {
        private var selectedRow: HealthData? = null
        private var healthDataList: List<HealthData> = emptyList()

        fun initializeData(context: Context) {
            if (healthDataList.isEmpty()) {
                healthDataList = readCSV(context)
            }
        }

        fun getSelectedRow(): HealthData? {
            if (selectedRow == null) {
                selectedRow = healthDataList.randomOrNull()
            }
            return selectedRow
        }

        fun updateSelectedRow() {
            selectedRow?.let { currentRow ->
                // Filter data berdasarkan kondisi
                val filteredData = healthDataList.filter { newRow ->
                    newRow.langkahKaki > currentRow.langkahKaki &&
                            newRow.jamTidur > currentRow.jamTidur &&
                            newRow.kaloriTerbakar > currentRow.kaloriTerbakar
                }

                // Jika ada data yang memenuhi, pilih salah satu secara acak
                if (filteredData.isNotEmpty()) {
                    selectedRow = filteredData.random()
                }
            } ?: run {
                // Jika selectedRow null (belum diinisialisasi), pilih data acak
                selectedRow = healthDataList.randomOrNull()
            }
        }

        private fun readCSV(context: Context): List<HealthData> {
            val healthDataList = mutableListOf<HealthData>()
            val inputStream = context.assets.open("dummy_health_data_200.csv")
            val reader = CSVReader(InputStreamReader(inputStream))

            // Skip header
            val rows = reader.readAll()
            for (i in 1 until rows.size) {
                val row = rows[i]
                healthDataList.add(
                    HealthData(
                        detakJantung = row[0].toInt(),
                        langkahKaki = row[1].toInt(),
                        jamTidur = row[2].toDouble(),
                        kaloriTerbakar = row[3].toInt()
                    )
                )
            }
            reader.close()
            return healthDataList
        }
    }
}
