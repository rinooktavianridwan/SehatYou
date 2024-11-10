package com.example.sehatyou

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatyou.ui.theme.SehatYouTheme

@Composable
fun SettingsPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonColors(Color.White, Color.Black, Color.White, Color.Black),
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .align(Alignment.End)
                    .padding(start = 0.dp, top = 15.dp, end = 20.dp, bottom = 0.dp),
            )
            {
                Icon(imageVector  = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }
            Text(
                text = "Setting",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue),
            ) {
                Column(
                    modifier = Modifier.padding(40.dp, 80.dp),
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
                            label = "Bahasa"
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
                            label = "Notifikasi"
                        )
                        SettingOption(
                            icon = painterResource(id = R.drawable.iconlogout),
                            label = "Log Out"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SettingOption(icon: Painter, label: String) {
    Column(
        modifier = Modifier
            .size(100.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
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