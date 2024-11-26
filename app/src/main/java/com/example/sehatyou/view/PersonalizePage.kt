package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizePage(navController: NavController = rememberNavController()) {
    var beratBadan = remember { mutableStateOf("55 kg") }
    var tinggiBadan = remember { mutableStateOf("174 cm") }
    var dateState = rememberDatePickerState()
    var openDialog = remember { mutableStateOf(false) }
    var tglLahir = remember { mutableStateOf("11/22/2024") }
    var jamKerjaStart = remember { mutableStateOf("") }
    var jamKerjaEnd = remember { mutableStateOf("") }

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(
                state = dateState
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5)),
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
                    Text(text = "Berat Badan")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = beratBadan.value,
                        onValueChange = {beratBadan.value = it},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {Text(beratBadan.value)}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Tinggi Badan")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = tinggiBadan.value,
                        onValueChange = {tinggiBadan.value = it},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {tinggiBadan.value}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Tanggal Lahir")

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = dateState.selectedDateMillis.toString(),
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.FFDEC5),
                            unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = {dateState.selectedDateMillis}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Jam Kerja")

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        OutlinedTextField(
                            value = jamKerjaStart.value,
                            onValueChange = {jamKerjaStart.value = it},
                            modifier = Modifier
                                .width(120.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.FFDEC5),
                                unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            placeholder = {Text(jamKerjaStart.value)},
                        )

                        Spacer(modifier = Modifier.width(100.dp))

                        OutlinedTextField(
                            value = jamKerjaEnd.value,
                            onValueChange = {jamKerjaEnd.value = it},
                            modifier = Modifier
                                .width(120.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorResource(id = R.color.FFDEC5),
                                unfocusedContainerColor = colorResource(id = R.color.FFDEC5),
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            placeholder = {Text(jamKerjaEnd.value)},
                        )
                    }

                    Spacer(modifier = Modifier.height(80.dp))

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
fun PreviewPersonalizeBox() {
    PersonalizePage()
}