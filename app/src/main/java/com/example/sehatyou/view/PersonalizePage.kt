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
    var dataUser by remember { mutableStateOf(UserEntity()) }

    // Load existing user data
    LaunchedEffect(Unit) {
        getUserDataFromFirebase(userId, context) { userEntity ->
            dataUser = userEntity
        }
    }

    var beratBadan by remember { mutableStateOf(dataUser.beratBadan) }
    var tinggiBadan by remember { mutableStateOf(dataUser.tinggiBadan) }
    var tglLahir by remember { mutableStateOf(dataUser.tglLahir) }
    var jamKerjaStart by remember { mutableStateOf(dataUser.jamKerjaStart) }
    var jamKerjaEnd by remember { mutableStateOf(dataUser.jamKerjaEnd) }

    LaunchedEffect(dataUser) {
        beratBadan = dataUser.beratBadan
        tinggiBadan = dataUser.tinggiBadan
        tglLahir = dataUser.tglLahir
        jamKerjaStart = dataUser.jamKerjaStart
        jamKerjaEnd = dataUser.jamKerjaEnd
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
                            dataUser.beratBadan = beratBadan
                            dataUser.tinggiBadan = tinggiBadan
                            dataUser.tglLahir = tglLahir
                            dataUser.jamKerjaStart = jamKerjaStart
                            dataUser.jamKerjaEnd = jamKerjaEnd
                            saveUserDataToFirebase(userId, dataUser, context)
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
