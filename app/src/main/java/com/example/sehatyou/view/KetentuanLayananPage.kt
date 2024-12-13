package com.example.sehatyou.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun KetentuanLayananPage(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp, 56.dp, 16.dp, 16.dp)) {
        // Title
        Text(
            text = "Ketentuan Layanan",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        Text(
            text = "1. Aplikasi ini disediakan 'sebagaimana adanya', tanpa jaminan apapun.\n\n" +
                    "2. Pengguna bertanggung jawab penuh terhadap penggunaan aplikasi ini.\n\n" +
                    "3. Penggunaan data pribadi akan diatur sesuai dengan kebijakan privasi yang berlaku.\n\n" +
                    "4. Pengguna setuju untuk tidak menggunakan aplikasi untuk tujuan ilegal atau tidak sah.\n\n" +
                    "5. Pengguna bertanggung jawab atas aktivitas yang terjadi pada akun mereka.\n\n" +
                    "6. Aplikasi berhak untuk menangguhkan atau menghentikan akun yang melanggar ketentuan layanan.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Button to go back
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KetentuanLayananPagePreview() {
    KetentuanLayananPage(navController = rememberNavController())
}
