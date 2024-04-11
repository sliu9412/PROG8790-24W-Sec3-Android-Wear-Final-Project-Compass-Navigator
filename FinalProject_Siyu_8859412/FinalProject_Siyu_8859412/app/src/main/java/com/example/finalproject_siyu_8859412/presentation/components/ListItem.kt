package com.example.finalproject_siyu_8859412.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Card
import com.example.finalproject_siyu_8859412.presentation.LocationHelper
import com.example.finalproject_siyu_8859412.presentation.StoreHelper
import com.example.finalproject_siyu_8859412.presentation.modals.LocationItem
import com.example.finalproject_siyu_8859412.presentation.router.Routes
import com.example.finalproject_siyu_8859412.presentation.ui.IconButton
import com.example.finalproject_siyu_8859412.presentation.ui.Label

@Composable
fun ListItem(
    index: Int,
    item: LocationItem,
    locationHelper: LocationHelper,
    storeHelper: StoreHelper,
    navController: NavHostController
) {

    // press the delete button
    val deleteItemHandler = {
        storeHelper.locationList.removeAt(index)
        storeHelper.storeList()
    }

    // press the navigate button
    val navigateHandler = {
        locationHelper.setTargetLocation(mutableStateOf(item))
        navController.navigate(Routes.COMPASS_SCREEN.toString())
    }

    Card(onClick = {}) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Label(
                modifier = Modifier.weight(1f),
                text = item.address
            )
            Row {
                IconButton(
                    icon = Icons.Default.Delete,
                    contentDescription = "delete",
                    size = 26,
                    action = deleteItemHandler
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(
                    icon = Icons.Default.Place,
                    contentDescription = "navigate",
                    size = 26,
                    action = navigateHandler
                )
            }
        }
    }
}