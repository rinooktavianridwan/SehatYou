import android.content.Context
import com.opencsv.CSVReader
import java.io.InputStreamReader

// Data class untuk menampung data
data class HealthData(
    val detakJantung: Int,
    val langkahKaki: Int,
    val jamTidur: Double,
    val kaloriTerbakar: Int
)

// Variabel global untuk menyimpan data yang dipilih
var selectedRow: HealthData? = null

// Fungsi untuk membaca CSV
fun readCSV(context: Context): List<HealthData> {
    val healthDataList = mutableListOf<HealthData>()
    val inputStream = context.assets.open("dummy_health_data.csv")
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

// Fungsi untuk memilih data secara acak di awal
fun selectRandomRow(dataList: List<HealthData>) {
    selectedRow = dataList.random()
}

// Fungsi untuk memperbarui data berdasarkan kondisi
fun updateSelectedRow(dataList: List<HealthData>) {
    selectedRow?.let { currentRow ->
        // Filter data berdasarkan kondisi
        val filteredData = dataList.filter { newRow ->
            newRow.langkahKaki > currentRow.langkahKaki &&
                    newRow.jamTidur > currentRow.jamTidur &&
                    newRow.kaloriTerbakar > currentRow.kaloriTerbakar
        }

        // Jika ada data yang memenuhi, pilih salah satu secara acak
        if (filteredData.isNotEmpty()) {
            selectedRow = filteredData.random()
        }
        // Jika tidak ada data yang memenuhi, data lama tetap digunakan
    } ?: run {
        // Jika selectedRow null (belum diinisialisasi), pilih data acak
        selectRandomRow(dataList)
    }
}
