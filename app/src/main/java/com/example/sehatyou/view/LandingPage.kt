package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.ui.theme.SehatYouTheme

@Composable
fun LandingPage(navController: NavController = rememberNavController()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background with gradient and image
        Image(
            painter = painterResource(id = R.drawable.landing_page_background), // Replace with your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),

        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo and text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your logo image
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You Are Enough",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold, // Membuat teks tebal
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp), // Geser teks ke kiri
                    textAlign = TextAlign.Start // Selaraskan ke kiri
                )
                Text(
                    text = "Just as You Are",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF3C1732),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.weight(2f))

            Column {

                Button(
                    onClick = { /* Handle Masuk */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3C1732),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "MASUK", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Handle Daftar */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF4A148C)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFF3C1732),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(text = "DAFTAR", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3C1732))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLandingPage() {
    SehatYouTheme {
        LandingPage()
    }
}
