package com.example.bedando1

import android.app.Activity
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var  imageView: ImageView

    lateinit var text: TextView

    val kockakepek: IntArray = intArrayOf(
        R.drawable.kocka1,
        R.drawable.kocka2,
        R.drawable.kocka3,
        R.drawable.kocka4,
        R.drawable.kocka5,
        R.drawable.kocka6
    )

    lateinit var random: Random
    var currentAcc = 0.0
    var prevAcc = 0.0
    var changeInAcc = 0.0
    var shake = false
    private lateinit var sensorManager: SensorManager
    private var acc = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            var y = event.values[0]
            var x = event.values[0]
            var z = event.values[0]

            currentAcc = Math.sqrt((x*x + y*y + z*z).toDouble())

            changeInAcc = Math.abs(prevAcc-currentAcc)
            prevAcc = currentAcc


            if (changeInAcc>13 && !shake)
            {
                GlobalScope.launch(Dispatchers.Main)
                {
                    for (i in 1..5){

                        delay(300)
                        imageView.setImageResource(kockakepek[random.nextInt(kockakepek.size)])
                    }

                }
                shake = true
            }
            else if (changeInAcc>13)
                shake = false
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

    }
    private var sensor: Sensor? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        random = Random

        imageView =  findViewById(R.id.imageView)

        imageView.setImageResource(kockakepek[0])


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(acc, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(acc)
    }

}