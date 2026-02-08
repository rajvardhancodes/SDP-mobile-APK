package com.smartdriver.profiler.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class MotionEvent(
    val type: EventType,
    val magnitude: Float,
    val timestamp: Long
)

enum class EventType {
    HARSH_BRAKE,
    RAPID_ACCELERATION,
    SHARP_TURN,
    NORMAL
}

data class MotionStats(
    val harshBrakes: Int,
    val rapidAccelerations: Int,
    val sharpTurns: Int,
    val avgAcceleration: Float,
    val maxAcceleration: Float
)

class MotionSensors(context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    // Counters
    var harshBrakes = 0; private set
    var rapidAccelerations = 0; private set
    var sharpTurns = 0; private set

    // Thresholds (m/s² for accelerometer, rad/s for gyroscope)
    companion object {
        const val HARSH_BRAKE_THRESHOLD = -6.0f      // strong deceleration
        const val RAPID_ACCEL_THRESHOLD = 4.0f        // strong acceleration
        const val SHARP_TURN_THRESHOLD = 5.0f         // lateral acceleration
        const val GYRO_TURN_THRESHOLD = 2.5f          // rotation rate

        // Cooldown to avoid counting same event multiple times
        const val EVENT_COOLDOWN_MS = 3000L            // 3 seconds
    }

    private var lastBrakeTime = 0L
    private var lastAccelTime = 0L
    private var lastTurnTime = 0L

    private val accelerationMagnitudes = mutableListOf<Float>()

    fun startListening(): Flow<MotionEvent> = callbackFlow {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_LINEAR_ACCELERATION -> {
                        val x = event.values[0]  // lateral (left/right)
                        val y = event.values[1]  // forward/backward
                        val z = event.values[2]  // up/down

                        val magnitude = Math.sqrt(
                            (x * x + y * y + z * z).toDouble()
                        ).toFloat()

                        accelerationMagnitudes.add(magnitude)
                        val now = System.currentTimeMillis()

                        // Detect HARSH BRAKING (negative Y-axis)
                        // Phone mounted vertically, Y points forward
                        if (y < HARSH_BRAKE_THRESHOLD && 
                            now - lastBrakeTime > EVENT_COOLDOWN_MS) {
                            harshBrakes++
                            lastBrakeTime = now
                            trySend(MotionEvent(
                                EventType.HARSH_BRAKE, 
                                Math.abs(y), 
                                now
                            ))
                        }

                        // Detect RAPID ACCELERATION (positive Y-axis)
                        if (y > RAPID_ACCEL_THRESHOLD && 
                            now - lastAccelTime > EVENT_COOLDOWN_MS) {
                            rapidAccelerations++
                            lastAccelTime = now
                            trySend(MotionEvent(
                                EventType.RAPID_ACCELERATION, 
                                y, 
                                now
                            ))
                        }

                        // Detect SHARP TURNS (X-axis lateral force)
                        if (Math.abs(x) > SHARP_TURN_THRESHOLD && 
                            now - lastTurnTime > EVENT_COOLDOWN_MS) {
                            sharpTurns++
                            lastTurnTime = now
                            trySend(MotionEvent(
                                EventType.SHARP_TURN, 
                                Math.abs(x), 
                                now
                            ))
                        }
                    }

                    Sensor.TYPE_GYROSCOPE -> {
                        val rotationRate = Math.sqrt(
                            (event.values[0] * event.values[0] +
                             event.values[1] * event.values[1] +
                             event.values[2] * event.values[2]).toDouble()
                        ).toFloat()

                        // Additional sharp turn detection via gyroscope
                        val now = System.currentTimeMillis()
                        if (rotationRate > GYRO_TURN_THRESHOLD && 
                            now - lastTurnTime > EVENT_COOLDOWN_MS) {
                            sharpTurns++
                            lastTurnTime = now
                            trySend(MotionEvent(
                                EventType.SHARP_TURN, 
                                rotationRate, 
                                now
                            ))
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        accelerometer?.let {
            sensorManager.registerListener(
                listener, it, SensorManager.SENSOR_DELAY_GAME  // ~20ms
            )
        }

        gyroscope?.let {
            sensorManager.registerListener(
                listener, it, SensorManager.SENSOR_DELAY_GAME
            )
        }

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }

    fun getStats(): MotionStats {
        return MotionStats(
            harshBrakes = harshBrakes,
            rapidAccelerations = rapidAccelerations,
            sharpTurns = sharpTurns,
            avgAcceleration = if (accelerationMagnitudes.isEmpty()) 0f 
                             else accelerationMagnitudes.average().toFloat(),
            maxAcceleration = accelerationMagnitudes.maxOrNull() ?: 0f
        )
    }

    fun reset() {
        harshBrakes = 0
        rapidAccelerations = 0
        sharpTurns = 0
        accelerationMagnitudes.clear()
    }
}
