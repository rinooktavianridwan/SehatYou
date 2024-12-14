package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.UserEntity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.example.sehatyou.utils.getUserDataFromFirebase
import com.example.sehatyou.utils.saveUserDataToFirebase

@Composable
fun ProfilePage(navController: NavController = rememberNavController()) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    // State untuk menampung data input pengguna
    var dataUser by remember { mutableStateOf(UserEntity()) }
    val currentUser = auth.currentUser
    var emailUser = ""
    var userId = ""

    if (currentUser != null) {
        userId = currentUser.uid
        emailUser = currentUser.email.toString()

        // Load existing user data
        LaunchedEffect(Unit) {
            getUserDataFromFirebase(userId, context) { userEntity ->
                dataUser = userEntity
            }
        }

        var namaLengkap by remember { mutableStateOf(dataUser.namaLengkap) }
        var noTelp by remember { mutableStateOf(dataUser.kontak) }

        // Update namaLengkap and noTelp when dataUser changes
        LaunchedEffect(dataUser) {
            namaLengkap = dataUser.namaLengkap
            noTelp = dataUser.kontak
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.F5F5F5)),
            //contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("home")
                    },
                    colors = IconButtonColors(Color.White, Color.Black, Color.White, Color.Black),
                    modifier = Modifier
                        .size(60.dp, 60.dp)
                        .align(Alignment.End)
                        .padding(start = 0.dp, top = 15.dp, end = 20.dp, bottom = 0.dp)
                        .shadow(
                            4.dp,
                            CircleShape,
                            ambientColor = Color.Black,
                            spotColor = Color.Black
                        ),
                )
                {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }

                Image(
                    painterResource(id = R.drawable.icon__profile_circle_),
                    "profile",
                    modifier = Modifier
                        .size(150.dp, 150.dp)
                        .padding(0.dp, 0.dp, 0.dp, 5.dp)
                        .align(Alignment.CenterHorizontally),

                    )

                Text(
                    text = "Ganti Gambar",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.purple423892)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                        .background(
                            colorResource(id = R.color.FFDEC5),
                            RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp, start = 25.dp, end = 25.dp, bottom = 0.dp)
                    ) {
                        Text(text = "Nama Pengguna")

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = namaLengkap,
                            onValueChange = { namaLengkap = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.FFDEC5),
                                unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(text = "Email")

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = emailUser,
                            onValueChange = { emailUser = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.FFDEC5),
                                unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            enabled = false
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(text = "Nomor Telepon")

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = noTelp,
                            onValueChange = { noTelp = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.FFDEC5),
                                unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Spacer(modifier = Modifier.height(35.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                                .padding(start = 45.dp, top = 0.dp, end = 45.dp, bottom = 0.dp),
                            onClick = {
                                navController.navigate("personalize")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.purple3C1732),
                                contentColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Personalisasi",
                                fontSize = 18.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                                .padding(start = 45.dp, top = 0.dp, end = 45.dp, bottom = 0.dp),
                            onClick = {
                                dataUser.namaLengkap = namaLengkap
                                dataUser.kontak = noTelp
                                saveUserDataToFirebase(userId, dataUser, context)
                                navController.navigate("profile")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.purple3C1732),
                                contentColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Perbarui",
                                fontSize = 18.sp,
                            )
                        }


                    }
                }
            }
        }
    }
}