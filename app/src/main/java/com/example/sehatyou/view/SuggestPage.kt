package com.example.sehatyou.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SuggestPage(navController: NavController = rememberNavController()) {
    var tanggal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5))
    ) {
        Column (modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 30.dp, end = 0.dp, bottom = 10.dp)) {
                Image (
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(235.dp))

                IconButton (
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "menu",
                        modifier = Modifier.size(55.dp)
                    )

                }
            }

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .background(
                        colorResource(id = R.color.FFDEC5),
                        RoundedCornerShape(60.dp, 60.dp, 0.dp, 0.dp)
                    )
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 50.dp, vertical = 20.dp)
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box (
                            modifier = Modifier
                                .background(colorResource(id = R.color.white), RoundedCornerShape(20.dp))
                        )
                        {Text (text = tanggal.toString(), Modifier.padding(10.dp, 5.dp))}

                        IconButton(
                            onClick = {
                                navController.navigate("home")
                            },
                            colors = IconButtonColors(Color.White, Color.Black, Color.White, Color.Black),
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                                .shadow(
                                    0.dp,
                                    CircleShape,
                                    ambientColor = Color.Black,
                                    spotColor = Color.Black
                                ),
                        )
                        {Icon(imageVector  = Icons.Filled.KeyboardArrowDown, contentDescription = "back", modifier = Modifier.size(20.dp,20.dp)) }
                    }
                    LazyColumn (
                        modifier = Modifier.fillMaxWidth()
                    ) {

                    }


                }

            }


        }

    }
}

@Preview
@Composable
fun SearchBoxPreview() {
    SuggestPage()
}