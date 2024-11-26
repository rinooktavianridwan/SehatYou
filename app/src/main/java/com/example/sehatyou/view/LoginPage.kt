package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R

@Composable
fun LoginPage(navController: NavController = rememberNavController()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE1CE))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Gambar logo otak dengan tanda plus
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Masuk",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3C1732),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )

        Text(
            text = "Halo! Selamat Datang",
            fontSize = 16.sp,
            color = Color(0xFF5A527B),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Input email
        Column {
            Text(text = "Alamat Email", fontSize = 14.sp, color = Color.Gray)
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input kata sandi
        Column {
            Text(text = "Kata Sandi", fontSize = 14.sp, color = Color.Gray)
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol masuk
        Button(
            onClick = { /* Handle login */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C1732))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Masuk dengan",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.gmail),
                    contentDescription = "Logo Gmail",
                    modifier = Modifier.size(24.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {
                append("Belum punya akun? ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Black
                    )
                ) {
                    append("Daftar sekarang")
                }
            },
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage()
}
