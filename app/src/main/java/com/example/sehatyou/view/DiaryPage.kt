package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R

@Composable
fun EditDiaryPage(navController: NavController = rememberNavController()) {
    var searchText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Diari",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                    color = Color(0xFF3C1732)
                )
                IconButton(onClick = { navController.popBackStack() }) { // Navigate back
                    Image(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = "Back",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Field
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFFFE0B2), shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = "Search",
                    tint = Color(0xFF3C1732),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Cari",
                                color = Color(0xFF3C1732),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of Diaries
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(3) { index ->
                    DiaryCard(
                        date = when (index) {
                            0 -> "28 Mei 21"
                            1 -> "29 Mei 21"
                            else -> "30 Mei 21"
                        },
                        title = when (index) {
                            0 -> "Hari Pertama Bekerja"
                            1 -> "Hari Kedua Bekerja"
                            else -> "Hari Terakhir Bekerja"
                        },
                        description = "Lorem ipsum yey kerja sit amet, consectetur adipiscing elit. Praesent volutpat eros in ligula egestas, in interdum magna volutpat. Integer eget felis euismod, porttitor sapien in, convallis erat. Sed vitae felis in sem condimentum placerat non...",
                        isFavorite = index % 2 == 0,
                        onEditClick = { navController.navigate("edit_screen/$index") }
                    )
                }
            }
        }

        // Floating Action Button
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
//                .padding(bottom = 10.dp)
                .zIndex(1f) // Ensure button appears on top
        ) {
            IconButton(
                onClick = { navController.navigate("add_screen") },
                modifier = Modifier
                    .size(100.dp) // Dynamic size for the button
                    .offset(x = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_plus),
                    contentDescription = "Add",
                    modifier = Modifier.size(100.dp) // Modify size here
                )
            }
        }
    }
}


@Composable
fun DiaryCard(
    date: String,
    title: String,
    description: String,
    isFavorite: Boolean,
    onEditClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(200.dp)
            .background(Color(0xFF3C1732), shape = MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C1732))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = if (isFavorite) painterResource(id = R.drawable.icon_star) else painterResource(id = R.drawable.icon_unstar),
                        contentDescription = "Favorite",
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(Color(0xFFFFE0B2), shape = MaterialTheme.shapes.small).padding(4.dp) // Background for date

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_clock), // Icon clock
                            contentDescription = "Clock",
                            tint = Color(0xFFFFFFFF),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = date,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFFFFFFF)
                        )
                    }
                }
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_ellipsis),
                        contentDescription = "Options",
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier.size(15.dp)
                    )
                }

                // Pop-up Menu
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            onEditClick()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_edit), // Edit icon
                                contentDescription = "Edit"
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Hapus") },
                        onClick = {
                            // Handle delete action
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_delete), // Delete icon
                                contentDescription = "Delete"
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFFFFFFF),
                maxLines = 4
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditDiaryScreen() {
    EditDiaryPage()
}