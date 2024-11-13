package com.example.sehatyou

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.ui.theme.SehatYouTheme

@Composable
fun SettingsPage(navController: NavController = rememberNavController()) {
    var showDialogBahasa by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Bahasa Indonesia") }
    var showDialogNotifikasi by remember { mutableStateOf(false) }
    var notificationInterval by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5))
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = {navController.navigate("home")},
                colors = IconButtonColors(Color.White, Color.Black, Color.White, Color.Black),
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .align(Alignment.TopEnd)
                    .padding(start = 0.dp, top = 20.dp, end = 20.dp, bottom = 0.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        clip = false
                    )
                    .background(Color.White, shape = CircleShape),
            )
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
            Text(
                text = "Pengaturan",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 20.dp)
                    .align(Alignment.Center),
                color = colorResource(id = R.color.F7B087)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(id = R.color.FFDEC5),
                    RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
                )
                .padding(0.dp, 40.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(40.dp, 80.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    SettingOption(
                        icon = painterResource(id = R.drawable.iconprofile),
                        label = "Profil"
                    )
                    SettingOption(
                        icon = painterResource(id = R.drawable.iconsmartwacth),
                        label = "SmartWatch"
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SettingOption(
                        icon = painterResource(id = R.drawable.iconlanguage),
                        label = "Bahasa",
                        onClick = { showDialogBahasa = true } // Menampilkan dialog saat diklik
                    )
                    SettingOption(
                        icon = painterResource(id = R.drawable.iconcustomerservice),
                        label = "Service"
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SettingOption(
                        icon = painterResource(id = R.drawable.iconnotification),
                        label = "Notifikasi",
                        onClick = { showDialogNotifikasi = true }
                    )
                    SettingOption(
                        icon = painterResource(id = R.drawable.iconlogout),
                        label = "Log Out"
                    )
                }
            }
        }
    }


    // Dialog untuk memilih bahasa
    if (showDialogBahasa) {
        LanguageSelectionDialog(
            currentLanguage = selectedLanguage,
            onDismiss = { showDialogBahasa = false },
            onSave = {
                showDialogBahasa = false
            },
            onLanguageSelected = { language ->
                selectedLanguage = language
            }
        )
    }
    // Dialog untuk memilih interval notifikasi
    if (showDialogNotifikasi) {
        NotificationIntervalDialog(
            interval = notificationInterval,
            onDismiss = { showDialogNotifikasi = false },
            onSave = {
                showDialogNotifikasi = false
            },
            onIntervalChanged = { newInterval ->
                notificationInterval = newInterval
            }
        )
    }
}

@Composable
fun NotificationIntervalDialog(
    interval: String,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onIntervalChanged: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Pilih Interval Notifikasi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.FFDEC5),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = interval,
                        onValueChange = onIntervalChanged,
                        textStyle = TextStyle(fontSize = 18.sp),
                        placeholder = { Text("......") },
                        singleLine = true,
                        modifier = Modifier
                            .width(60.dp)
                            .background(Color.Transparent),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "notifikasi per hari",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.F7B087), // Change background color
                    contentColor = Color.White // Change text/icon color
                ),
            ) {
                Text("Simpan", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}


@Composable
fun LanguageSelectionDialog(
    currentLanguage: String,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Pilih Bahasa",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colorResource(id = R.color.FFDEC5),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.FFDEC5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onLanguageSelected("Bahasa Indonesia") }
                        .padding(16.dp, 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Bahasa Indonesia")
                    Checkbox(
                        checked = currentLanguage == "Bahasa Indonesia",
                        onCheckedChange = { if (it) onLanguageSelected("Bahasa Indonesia") }
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.FFDEC5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onLanguageSelected("Bahasa Inggris") }
                        .padding(16.dp, 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Bahasa Inggris")
                    Checkbox(
                        checked = currentLanguage == "Bahasa Inggris",
                        onCheckedChange = { if (it) onLanguageSelected("Bahasa Inggris") }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.F7B087), // Change background color
                    contentColor = Color.White // Change text/icon color
                ),
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}


@Composable
fun SettingOption(icon: Painter, label: String, onClick: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .size(140.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(8.dp),
                clip = false
            )
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick?.invoke() }, // Menambahkan clickable untuk tombol
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SehatYouTheme {
        SettingsPage()
    }
}