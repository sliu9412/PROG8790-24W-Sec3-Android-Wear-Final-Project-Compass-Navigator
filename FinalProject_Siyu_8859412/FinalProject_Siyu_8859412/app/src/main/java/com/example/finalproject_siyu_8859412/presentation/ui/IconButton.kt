package com.example.finalproject_siyu_8859412.presentation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon

@Composable
fun IconButton(
    icon: ImageVector,
    contentDescription: String,
    action: () -> Unit,
    size: Int = 40,
    enable: Boolean = true
) {
    Button(modifier = Modifier.size(size.dp), enabled = enable, onClick = action) {
        Icon(modifier = Modifier.size((size - 8).dp), imageVector = icon, contentDescription = contentDescription)
    }
}