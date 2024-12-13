package com.example.sehatyou.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.UserEntity
import com.example.sehatyou.utils.getUserDataFromFirebase
import com.example.sehatyou.utils.saveUserDataToFirebase
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizePage(navController: NavController = rememberNavController()) {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // State untuk menampung data input pengguna
    var beratBadan by remember { mutableStateOf("55 kg") }
    var tinggiBadan by remember { mutableStateOf("174 cm") }
    var tglLahir by remember { mutableStateOf("11/22/2024") }
    var jamKerjaStart by remember { mutableStateOf("09:00") }
    var jamKerjaEnd by remember { mutableStateOf("17:00") }

    // Load existing user data
    LaunchedEffect(Unit) {
        getUserDataFromFirebase(userId, context) { userEntity ->
            beratBadan = userEntity.beratBadan
            tinggiBadan = userEntity.tinggiBadan
            tglLahir = userEntity.tglLahir
            jamKerjaStart = userEntity.jamKerjaStart
            jamKerjaEnd = userEntity.jamKerjaEnd
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Tombol kembali
            IconButton(
                onClick = { navController.navigate("setting") },
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }

            // Box untuk form input
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .background(
                        colorResource(id = R.color.FFDEC5),
                        RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(25.dp)) {
                    Text(text = "Berat Badan")
                    OutlinedTextField(
                        value = beratBadan,
                        onValueChange = { beratBadan = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Tinggi Badan")
                    OutlinedTextField(
                        value = tinggiBadan,
                        onValueChange = { tinggiBadan = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Tanggal Lahir")
                    OutlinedTextField(
                        value = tglLahir,
                        onValueChange = { tglLahir = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Jam Kerja")
                    Row {
                        OutlinedTextField(
                            value = jamKerjaStart,
                            onValueChange = { jamKerjaStart = it },
                            modifier = Modifier.width(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        OutlinedTextField(
                            value = jamKerjaEnd,
                            onValueChange = { jamKerjaEnd = it },
                            modifier = Modifier.width(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    // Tombol Perbarui
                    Button(
                        onClick = {
                            val userEntity = UserEntity(
                                beratBadan = beratBadan,
                                tinggiBadan = tinggiBadan,
                                tglLahir = tglLahir,
                                jamKerjaStart = jamKerjaStart,
                                jamKerjaEnd = jamKerjaEnd
                            )
                            saveUserDataToFirebase(userId, userEntity, context)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple3C1732))
                    ) {
                        Text(text = "Perbarui", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPersonalizePage() {
    PersonalizePage()
}
