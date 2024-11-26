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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatyou.R

@Composable
fun ProfileBox() {
    var namaUser = remember { mutableStateOf("Anton") }
    var emailUser = remember { mutableStateOf("Anton@bletak.com") }
    var noHpUser = remember { mutableStateOf("08123456789") }
    var passwordUser = remember { mutableStateOf("AkunBaru") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5)),
        //contentAlignment = Alignment.Center
        ) {

        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonColors(Color.White, Color.Black, Color.White, Color.Black),
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .align(Alignment.End)
                    .padding(start = 0.dp, top = 15.dp, end = 20.dp, bottom = 0.dp)
                    .shadow(4.dp, CircleShape, ambientColor = Color.Black, spotColor = Color.Black),
                )
            {
                Icon(imageVector  = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }

            Image (
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

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .background(
                        colorResource(id = R.color.FFDEC5),
                        RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
                    )
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp, start = 25.dp, end = 25.dp, bottom = 0.dp)
                ){
                    Text(text = "Nama Pengguna")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = namaUser.value,
                        onValueChange = {namaUser.value = it},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {Text(namaUser.value)}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Email")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = emailUser.value,
                        onValueChange = {emailUser.value = it},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {Text(emailUser.value)}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Nomor Telepon")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = noHpUser.value,
                        onValueChange = {noHpUser.value = it},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {Text(noHpUser.value)}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Kata Sandi")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = passwordUser.value,
                        onValueChange = {passwordUser.value = it},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {Text(passwordUser.value)},
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    Button (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .padding(start = 45.dp, top = 0.dp, end = 45.dp, bottom = 0.dp),
                        onClick = { /*TODO*/ },
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

                    Button (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .padding(start = 45.dp, top = 0.dp, end = 45.dp, bottom = 0.dp),
                        onClick = { /*TODO*/ },
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

@Preview
@Composable
fun PreviewProfileBox() {
    ProfileBox()
}