package com.example.sehatyou.view

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterPage(navController: NavController = rememberNavController()) {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3C1732), Color(0xFFD5BBC5)),
                    startY = 7f,
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
            // Judul
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

            // Nama Lengkap
            InputField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Nama Lengkap",
                placeholder = "nama lengkap"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            InputField(
                value = email,
                onValueChange = { email = it },
                label = "Alamat Email",
                placeholder = "alamat email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            InputField(
                value = password,
                onValueChange = { password = it },
                label = "Kata Sandi",
                placeholder = "kata sandi",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Re-enter Password
            InputField(
                value = rePassword,
                onValueChange = { rePassword = it },
                label = "Ulangi Kata Sandi",
                placeholder = "re-enter password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Submit
            Button(
                onClick = {
                    if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Harap lengkapi semua kolom", Toast.LENGTH_SHORT).show()
                    } else if (!acceptTerms) {
                        Toast.makeText(context, "Harap setujui syarat dan ketentuan", Toast.LENGTH_SHORT).show()
                    } else if (password == rePassword) {
                        registerUser(email, password, fullName, context, navController)
                    } else {
                        Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                    }
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

            // Syarat dan Ketentuan
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(checkmarkColor = Color(0xFF4D0051))
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Saya menyetujui ",
                    color = Color.White
                )
                Text(
                    text = "syarat",
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate("terms") }
                )
                Text(
                    text = " dan ",
                    color = Color.White
                )
                Text(
                    text = "ketentuan layanan.",
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate("terms_and_conditions") }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Teks Masuk
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sudah punya akun?", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Masuk",
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate("login") }
                )
            }
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        singleLine = true,
        shape = MaterialTheme.shapes.small
    )
}

private fun registerUser(email: String, password: String, fullName: String, context: Context, navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val db = FirebaseFirestore.getInstance()
                val userData = hashMapOf(
                    "fullName" to fullName,
                    "email" to email
                )
                db.collection("users").document(user!!.uid)
                    .set(userData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Pendaftaran gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    RegisterPage()
}
