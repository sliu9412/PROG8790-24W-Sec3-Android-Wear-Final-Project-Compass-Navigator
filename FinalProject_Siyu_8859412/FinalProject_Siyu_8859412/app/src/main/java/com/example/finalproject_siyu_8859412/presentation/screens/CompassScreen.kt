package com.example.finalproject_siyu_8859412.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.finalproject_siyu_8859412.R
import com.example.finalproject_siyu_8859412.presentation.LocationHelper

@Composable
fun CompassScreen(locationHelper: LocationHelper) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        if (locationHelper.isPermitted) {
            Column(
                modifier = Modifier.zIndex(0f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.width(140.dp),
                    text = locationHelper.getTargetLocationAddress(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = locationHelper.distance)
            }
            Image(
                modifier = Modifier
                    .rotate(locationHelper.direction.toFloat())
                    .zIndex(1f),
                painter = painterResource(R.drawable.compass),
                contentDescription = "compass",
            )
        } else {
            Text(text = "GPS permission is denied, Please enable it")
        }
    }
}
