package com.example.sehatyou.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatyou.R

@Composable
fun SuggestionDetailPopup(
    title: String,
    description: String,
    date: String,
    isStarred: Boolean,
    onDismiss: () -> Unit,
    onStarToggle: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth(),
        onDismissRequest = { onDismiss() },
        confirmButton = {},
        containerColor = Color.White,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = date)
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
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .verticalScroll(rememberScrollState()) // Scrollable area
                        .background(Color.LightGray.copy(alpha = 0.2f)) // Optional background
                        .padding(8.dp)
                ) {
                    Text(
                        text = description,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            onDelete() // Memanggil fungsi hapus
                            onDismiss() // Tutup popup setelah hapus
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Hapus")
                    }
                    Button(
                        onClick = { onDismiss() }, // Tutup popup
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Close")
                    }
                }
            }
        },
    )
}