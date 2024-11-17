package com.example.sehatyou

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen() {
    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val rePassword = remember { mutableStateOf("") }
    val acceptTerms = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4D0051), Color(0xFFBCA3C2))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Daftar",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Welcome text
            Text(
                text = "Halo!\nSelamat Datang",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Full Name Input
            TextField(
                value = fullName.value,
                onValueChange = { fullName.value = it },
                label = { Text("Nama Lengkap") },
                placeholder = { Text("nama lengkap") },
                modifier = Modifier.fillMaxWidth(),
                isError = fullName.value.isBlank()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Input
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Alamat Email") },
                placeholder = { Text("alamat email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = email.value.isBlank() || !email.value.contains("@")
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Kata Sandi") },
                placeholder = { Text("kata sandi") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = password.value.isBlank()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Re-enter Password Input
            TextField(
                value = rePassword.value,
                onValueChange = { rePassword.value = it },
                label = { Text("Ulangi Kata Sandi") },
                placeholder = { Text("re-enter password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = password.value != rePassword.value
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    when {
                        fullName.value.isBlank() -> errorMessage.value = "Nama lengkap tidak boleh kosong"
                        email.value.isBlank() || !email.value.contains("@") -> errorMessage.value = "Email tidak valid"
                        password.value.isBlank() -> errorMessage.value = "Kata sandi tidak boleh kosong"
                        password.value != rePassword.value -> errorMessage.value = "Kata sandi tidak cocok"
                        !acceptTerms.value -> errorMessage.value = "Anda harus menyetujui syarat dan ketentuan"
                        else -> errorMessage.value = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4D0051)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "SUBMIT", color = Color.White)
            }

            if (errorMessage.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage.value,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Terms and Conditions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptTerms.value,
                    onCheckedChange = { acceptTerms.value = it },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color(0xFF4D0051))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Saya menyetujui ",
                    color = Color.Gray
                )
                Text(
                    text = "syarat",
                    color = Color(0xFF4D0051),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Handle terms click */ }
                )
                Text(
                    text = " dan ",
                    color = Color.Gray
                )
                Text(
                    text = "ketentuan layanan.",
                    color = Color(0xFF4D0051),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Handle policies click */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Prompt
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sudah punya akun?", color = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Masuk",
                    color = Color(0xFF4D0051),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Handle navigation to login */ }
                )
            }
        }
    }
}
