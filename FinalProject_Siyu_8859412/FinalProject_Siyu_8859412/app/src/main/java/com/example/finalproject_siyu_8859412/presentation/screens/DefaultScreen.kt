package com.example.finalproject_siyu_8859412.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.finalproject_siyu_8859412.R
import com.example.finalproject_siyu_8859412.presentation.LocationHelper
import com.example.finalproject_siyu_8859412.presentation.StoreHelper
import com.example.finalproject_siyu_8859412.presentation.modals.LocationItem
import com.example.finalproject_siyu_8859412.presentation.router.Routes
import com.example.finalproject_siyu_8859412.presentation.ui.IconButton
import com.example.finalproject_siyu_8859412.presentation.ui.Label

@Composable
fun DefaultScreen(
    navController: NavHostController,
    locationHelper: LocationHelper,
    storeHelper: StoreHelper
) {

    // When press the add button
    val addNewLocationHandler = {
        val currentLocation = locationHelper.getCenterLocation()
        if (currentLocation is LocationItem) {
            storeHelper.locationList.add(0, currentLocation)
            storeHelper.storeList()
            navController.navigate(Routes.LIST_SCREEN.toString())
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (locationHelper.isLoading) {
            Label(text = "loading...")
        } else {
            Image(
                modifier = Modifier.width(80.dp),
                painter = painterResource(id = R.drawable.logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                contentDescription = "logo"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Welcome to")
            Text(text = "ShareParking")
            Text(text = "Helper")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                IconButton(
                    icon = Icons.Default.Menu,
                    contentDescription = "list",
                    action = { navController.navigate(Routes.LIST_SCREEN.toString()) })
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    icon = Icons.Default.Add,
                    contentDescription = "location",
                    action = addNewLocationHandler
                )
            }
        }

    }
}