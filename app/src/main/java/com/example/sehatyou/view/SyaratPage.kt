package com.example.sehatyou.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SyaratPage(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp, 56.dp, 16.dp, 16.dp)) {
        // Title
        Text(
            text = "Syarat Penggunaan Layanan",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        Text(
            text = "1. Pengguna harus berusia minimal 18 tahun untuk menggunakan aplikasi ini.\n\n" +
                    "2. Pengguna harus menyetujui ketentuan yang berlaku di aplikasi ini.\n\n" +
                    "3. Aplikasi ini hanya untuk penggunaan pribadi dan tidak untuk tujuan komersial.\n\n" +
                    "4. Dilarang melakukan tindakan yang merusak layanan atau sistem aplikasi.\n\n" +
                    "5. Aplikasi ini berhak untuk mengubah syarat penggunaan setiap saat tanpa pemberitahuan lebih lanjut.",
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
fun SyaratPagePreview() {
    SyaratPage(navController = rememberNavController())
}
