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
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var  imageView: ImageView

    val kockakepek: IntArray = intArrayOf(
        R.drawable.kocka1,
        R.drawable.kocka2,
        R.drawable.kocka3,
        R.drawable.kocka4,
        R.drawable.kocka5,
        R.drawable.kocka6
    )

    lateinit var random: Random

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        random = Random

        imageView =  findViewById(R.id.imageView)

        imageView.setImageResource(kockakepek[random.nextInt(kockakepek.size)])

        imageView.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main)
            {
                for (i in 1..10){

                    delay(180)
                    imageView.setImageResource(kockakepek[random.nextInt(kockakepek.size)])
                }

            }
        }
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }


}