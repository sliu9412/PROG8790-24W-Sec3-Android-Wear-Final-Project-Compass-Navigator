/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.finalproject_siyu_8859412.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.finalproject_siyu_8859412.presentation.modals.LocationItem
import com.example.finalproject_siyu_8859412.presentation.router.Router
import com.example.finalproject_siyu_8859412.presentation.screens.DefaultScreen
import com.example.finalproject_siyu_8859412.presentation.screens.ListScreen
import com.example.finalproject_siyu_8859412.presentation.theme.FinalProject_Siyu_8859412Theme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationHelper = LocationHelper(this)
        val storeHelper = StoreHelper(this)
        storeHelper.loadList()
        setContent {
            Main(locationHelper, storeHelper)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Main(locationHelper: LocationHelper, storeHelper: StoreHelper) {
    FinalProject_Siyu_8859412Theme {
        val currentLocation = mutableStateOf<LocationItem?>(null)
        locationHelper.setCenterLocation(currentLocation)
        locationHelper.startUpdateCoordinate()

        Scaffold {
            Router(locationHelper, storeHelper)
        }
    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val locationHelper = LocationHelper(context)
    val storeHelper = StoreHelper(context)
    Main(locationHelper, storeHelper)
}