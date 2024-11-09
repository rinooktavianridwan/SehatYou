package com.example.sehatyou

import android.provider.MediaStore.Images
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileBox() {
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
                    .padding(start = 0.dp, top = 15.dp, end = 20.dp, bottom = 0.dp),
                )
            {
                Icon(imageVector  = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }

            Image (
                painterResource(id = R.drawable.icon__profile_circle_),
                "profile",
                modifier = Modifier
                    .size(130.dp, 130.dp)
                    .padding(0.dp, 0.dp, 0.dp, 5.dp)
                    .align(Alignment.CenterHorizontally),

            )

            Text(
                text = "Ganti Gambar",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = colorResource(id = R.color.purple423892)
                )
        }
    }
}

@Preview
@Composable
fun PreviewProfileBox() {
    ProfileBox()
}