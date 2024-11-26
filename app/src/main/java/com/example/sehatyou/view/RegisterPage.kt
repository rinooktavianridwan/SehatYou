package com.example.sehatyou.view
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterPage(navController: NavController = rememberNavController()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3C1732),Color(0xFFE7DFDC)),
                    startY = 0f, // Start of gradient at the top
                    endY = Float.POSITIVE_INFINITY
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
            Text(
                text = "Daftar",
                style = TextStyle(
                    fontWeight = FontWeight.Black,
                    fontSize = 40.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .offset(y = (-15).dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Halo!\nSelamat Datang",
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            var fullName by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var rePassword by remember { mutableStateOf("") }
            var acceptTerms by remember { mutableStateOf(false) }

            TextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Nama Lengkap") },
                placeholder = { Text("nama lengkap") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Alamat Email") },
                placeholder = { Text("alamat email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Kata Sandi") },
                placeholder = { Text("kata sandi") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = rePassword,
                onValueChange = { rePassword = it },
                label = { Text("Ulangi Kata Sandi") },
                placeholder = { Text("re-enter password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C1732)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "SUBMIT", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color(0xFF4D0051))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Saya menyetujui ",
                    color = Color.Gray
                )
                Text(
                    text = "syarat",
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Handle terms click */ }
                )
                Text(
                    text = " dan ",
                    color = Color.Gray
                )
                Text(
                    text = "ketentuan layanan.",
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Handle policies click */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sudah punya akun?", color = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Masuk",
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Handle navigation to login */ }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    RegisterPage()
}
