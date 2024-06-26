package ru.spbstu.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.spbstu.weatherapp.domain.location.LocationTracker
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).apply {
                if (isComplete) {
                    Log.d("LocationTracker", "isComplete: true")
                    if (isSuccessful && result != null) {
                        Log.d("LocationTracker", "Location successful: $result")
                        cont.resume(result)
                    } else {
                        Log.e("LocationTracker", "Location failed or result is null")
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    if (it != null) {
                        Log.d("LocationTracker", "Location successful: $it")
                        cont.resume(it)
                    } else {
                        Log.e("LocationTracker", "Location result is null")
                        cont.resume(null)
                    }
                }
                addOnFailureListener {
                    Log.e("LocationTracker", "Location failed: ${it.message}")
                    cont.resume(null)
                }
                addOnCanceledListener {
                    Log.e("LocationTracker", "Location request canceled")
                    cont.cancel()
                }
            }
        }
    }

    override suspend fun getCityName(lat: Double, long: Double): String? {
        TODO("Not yet implemented")
    }
}