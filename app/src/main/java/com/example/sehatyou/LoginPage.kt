package com.example.sehatyou

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.ui.theme.SehatYouTheme

@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Masuk",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Alamat Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Kata Sandi") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (email == "user@example.com" && password == "password123") {
                    navController.navigate("home")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFf44336))
        ) {
            Text(text = "MASUK")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Masuk dengan Google")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Belum punya akun? Daftar sekarang")
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Selamat datang di halaman utama!",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController = navController) }
        composable("home") { HomeScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    SehatYouTheme {
        LoginPage(navController = rememberNavController())
    }
}
