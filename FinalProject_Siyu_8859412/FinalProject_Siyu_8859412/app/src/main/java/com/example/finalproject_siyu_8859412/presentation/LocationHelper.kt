package com.example.finalproject_siyu_8859412.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.example.finalproject_siyu_8859412.presentation.modals.LocationItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.concurrent.TimeUnit
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * To update user's GPS coordinate continuously
 */
class LocationHelper(context: Context) {
    private var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest
    private var locationCallback: LocationCallback
    var currentLocation: Location? = null
    var isPermitted = false
    private lateinit var centerLocation: MutableState<LocationItem?>
    private var targetLocation: MutableState<LocationItem?> = mutableStateOf(null)

    // The distance from center location to target location
    val distance: String
        get() {
            return if (centerLocation.value !== null && targetLocation.value !== null) {
                val meterDistance =
                    distanceConvertor(centerLocation.value!!, targetLocation.value!!).toInt()
                if (meterDistance <= 1000) {
                    meterDistance.toString() + "M"
                } else {
                    val kiloMeterDistance = "%.2f".format(meterDistance / 1000.0)
                    kiloMeterDistance + "KM"
                }
            } else {
                ""
            }
        }

    // The angel between center location and target location
    val direction: Number
        get() {
            return if (centerLocation.value !== null && targetLocation.value !== null) {
                directionConvertor(centerLocation.value!!, targetLocation.value!!)
            } else {
                -1.0
            }
        }

    // To wait the first lastLocation object generating
    val isLoading: Boolean
        get() {
            return centerLocation.value == null
        }

    fun getTargetLocationAddress(): String {
        val targetLocationObject = targetLocation.value
        return if (targetLocationObject !== null) {
            targetLocationObject.address
        } else {
            ""
        }
    }

    fun getCenterLocation(): LocationItem? {
        return centerLocation.value
    }

    // bind the mutable state and make it update with latest location
    fun setCenterLocation(centerLocation: MutableState<LocationItem?>) {
        this.centerLocation = centerLocation
    }

    // bind the target location and calculate distance and direction
    fun setTargetLocation(targetLocation: MutableState<LocationItem?>) {
        this.targetLocation = targetLocation
    }

    // subscribe location updating task
    @SuppressLint("MissingPermission")
    fun startUpdateCoordinate() {
        if (isPermitted) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        // set the update frequency
        locationRequest = LocationRequest.Builder(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            TimeUnit.SECONDS.toMillis(2)
        ).build()
        // update the latest location
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                currentLocation = p0.lastLocation
                if (currentLocation is Location) {
                    centerLocation.value =
                        LocationItem(
                            currentLocation!!.latitude,
                            currentLocation!!.longitude,
                            addressConvertor(
                                context,
                                currentLocation!!.latitude,
                                currentLocation!!.longitude
                            )
                        )
                }
            }
        }
        // permission check
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).toTypedArray(), 1
            )
        } else {
            isPermitted = true
        }
    }

    companion object {
        // To convert coordinates into address string
        fun addressConvertor(context: Context, latitude: Double, longitude: Double): String {
            val geocoder = Geocoder(context)
            val address = geocoder.getFromLocation(latitude, longitude, 1)?.get(0)
            return if (address == null) {
                "Unknown Address"
            } else {
                val stringBuilder = StringBuilder()
                for (line in 0..address.maxAddressLineIndex) {
                    stringBuilder.append(address.getAddressLine(line))
                }
                return stringBuilder.toString()
            }
        }

        // To calculate the distance of two GPS coordinates
        fun distanceConvertor(centerPoint: LocationItem, targetPoint: LocationItem): Double {
            fun haversine(location1: LocationItem, location2: LocationItem): Double {
                val r = 6371 * 1000 // The radius of the earth, unit: meter
                val latDistance = Math.toRadians(location2.latitude - location1.latitude)
                val lonDistance = Math.toRadians(location2.longitude - location1.longitude)
                val a = sin(latDistance / 2) * sin(latDistance / 2) +
                        (cos(Math.toRadians(location1.latitude)) * cos(Math.toRadians(location2.latitude)) *
                                sin(lonDistance / 2) * sin(lonDistance / 2))
                val c = 2 * atan2(sqrt(a), sqrt(1 - a))
                return r * c
            }

            return haversine(centerPoint, targetPoint)
        }

        // To calculate the direction of two GPS coordinates
        fun directionConvertor(centerPoint: LocationItem, targetPoint: LocationItem): Double {
            fun calculateBearing(start: LocationItem, end: LocationItem): Double {
                val lat1 = Math.toRadians(start.latitude)
                val lon1 = Math.toRadians(start.longitude)
                val lat2 = Math.toRadians(end.latitude)
                val lon2 = Math.toRadians(end.longitude)
                val deltaLon = lon2 - lon1
                val y = sin(deltaLon) * cos(lat2)
                val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLon)
                var bearing = atan2(y, x)
                bearing = Math.toDegrees(bearing)
                bearing = (bearing + 360) % 360
                return bearing
            }
            return calculateBearing(centerPoint, targetPoint)
        }
    }
}