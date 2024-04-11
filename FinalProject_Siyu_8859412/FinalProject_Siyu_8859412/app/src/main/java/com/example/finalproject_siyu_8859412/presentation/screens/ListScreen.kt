package com.example.finalproject_siyu_8859412.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.material.Text
import com.example.finalproject_siyu_8859412.presentation.LocationHelper
import com.example.finalproject_siyu_8859412.presentation.StoreHelper
import com.example.finalproject_siyu_8859412.presentation.components.ListItem
import com.example.finalproject_siyu_8859412.presentation.ui.Label
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navController: NavHostController,
    locationHelper: LocationHelper,
    storeHelper: StoreHelper
) {
    val state = ScalingLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Stored")
            Text(text = "Locations")
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                if (storeHelper.locationList.size > 0) {
                    ScalingLazyColumn(state = state) {
                        itemsIndexed(storeHelper.locationList) { index, item ->
                            ListItem(index, item, locationHelper, storeHelper, navController)
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    Label(text = "You have not stored any locations")
                }
            }
        }

        coroutineScope.launch {
            state.scrollToItem(0)
        }
    }
}