package com.example.sehatyou.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatyou.R

@Composable
fun SuggestionCard(
    title: String,
    deskripsi: String,
    tanggal: String,
    isStarred: Boolean,
    onStarToggle: () -> Unit,
    waktu: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(bottom = 0.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .background(colorResource(id = R.color.DDE7FF), CircleShape)
                ) {
                    // Bintang untuk border
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                    )
                    //Bintang
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = { onStarToggle() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite",
                            tint = if (isStarred) Color.Yellow else colorResource(id = R.color.DDE7FF),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = deskripsi,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp))
                .padding(0.dp)
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = tanggal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.Gray)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = waktu,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun ActivityCard(title: String, subtitle: String, icon: Int, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .size(160.dp, 80.dp)
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(8.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.width(60.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.F7B087)
                )
                Text(text = subtitle, fontSize = 12.sp, color = colorResource(id = R.color.F7B087))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )
        }
    }
}