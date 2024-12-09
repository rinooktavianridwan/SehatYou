package com.example.sehatyou.view

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginPage(navController: NavController = rememberNavController()) {
    val context = LocalContext.current
    val activity = context as Activity

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Google Sign-In Client
    val googleSignInClient: GoogleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }

    // Google Sign-In Result Handler
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java) // Throws exception if failed
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        Log.d("LoginPage", "Google Sign-In successful")
                        navController.navigate("home")
                    } else {
                        errorMessage = "Google Sign-In failed: ${authTask.exception?.message}"
                    }
                }
        } catch (e: Exception) {
            errorMessage = "Google Sign-In failed: ${e.message}"
            Log.e("LoginPage", "Google Sign-In failed", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE1CE))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Masuk",
            fontSize = 32.sp,
            color = Color(0xFF3C1732),
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
        )

        Text(
            text = "Halo!\nSelamat Datang",
            fontSize = 16.sp,
            color = Color(0xFF5A527B),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Field
        Text(
            text = "Alamat Email",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        Text(
            text = "Kata Sandi",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Login Button
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Email dan kata sandi harus diisi!"
                } else {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("home")
                            } else {
                                errorMessage = "Login gagal: ${task.exception?.message}"
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C1732))
        ) {
            Text(text = "MASUK", fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Google Sign-In Button
        Button(
            onClick = {
                // Force sign-out before prompting user to select an account
                GoogleSignIn.getLastSignedInAccount(context)?.let {
                    googleSignInClient.signOut()
                }
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C1732))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Masuk dengan", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.gmail),
                    contentDescription = "Logo Gmail",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Belum punya akun? ", fontSize = 14.sp, color = Color.Gray)
            Text(
                text = "Daftar sekarang",
                fontSize = 14.sp,
                color = Color(0xFF3C1732),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
    }
}
