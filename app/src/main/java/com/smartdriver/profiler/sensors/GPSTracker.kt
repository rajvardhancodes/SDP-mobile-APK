package com.smartdriver.profiler.sensors

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class GPSData(
    val latitude: Double,
    val longitude: Double,
    val speed: Float,          // m/s from GPS
    val speedKmh: Float,      // converted to km/h
    val altitude: Double,
    val bearing: Float,        // direction of travel
    val accuracy: Float,
    val timestamp: Long
)

class GPSTracker(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        1000L  // 1 second interval
    ).apply {
        setMinUpdateDistanceMeters(5f)   // minimum 5 meters
        setWaitForAccurateLocation(true)
    }.build()

    private var previousLocation: Location? = null
    var totalDistance: Float = 0f        // meters
        private set
    var maxSpeed: Float = 0f            // km/h
        private set
    private val speedReadings = mutableListOf<Float>()

    @SuppressLint("MissingPermission")
    fun startTracking(): Flow<GPSData> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    
                    // Calculate distance from previous point
                    previousLocation?.let { prev ->
                        totalDistance += prev.distanceTo(location)
                    }
                    previousLocation = location

                    // Speed
                    val speedKmh = if (location.hasSpeed()) {
                        location.speed * 3.6f  // m/s to km/h
                    } else {
                        0f
                    }
                    
                    maxSpeed = maxOf(maxSpeed, speedKmh)
                    speedReadings.add(speedKmh)

                    val gpsData = GPSData(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        speed = location.speed,
                        speedKmh = speedKmh,
                        altitude = location.altitude,
                        bearing = location.bearing,
                        accuracy = location.accuracy,
                        timestamp = location.time
                    )

                    trySend(gpsData)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }

    fun getSpeedReadings(): List<Float> = speedReadings.toList()

    fun getAverageSpeed(): Float {
        return if (speedReadings.isEmpty()) 0f
        else speedReadings.average().toFloat()
    }

    fun getSpeedStdDev(): Float {
        if (speedReadings.size < 2) return 0f
        val mean = speedReadings.average()
        val variance = speedReadings.map { (it - mean) * (it - mean) }.average()
        return Math.sqrt(variance).toFloat()
    }

    fun reset() {
        previousLocation = null
        totalDistance = 0f
        maxSpeed = 0f
        speedReadings.clear()
    }
}
