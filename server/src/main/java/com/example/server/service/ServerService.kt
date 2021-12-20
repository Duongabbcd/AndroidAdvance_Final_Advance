package com.example.server.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.server.utils.Constant.DEFAULT_LOCATION
import com.example.lequangdao.IMyServer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ServerService : Service() {
    private lateinit var fuseLocationClient: FusedLocationProviderClient
    override fun onCreate() {
        super.onCreate()
        fuseLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private val binder = object : IMyServer.Stub() {
        override fun operation(): Int {
            val n = 1000
            process()
            return (n * (n + 1)) / 2
        }

        override fun process() {
            Thread.sleep(5000L)
        }

        @SuppressLint("MissingPermission")
        override fun ocateMe(): String {
            var locationResult = DEFAULT_LOCATION
            var isSuccess = false

            while (!isSuccess) {
                fuseLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        locationResult = it.latitude.toString() + " - " + it.longitude

                    }
                    isSuccess = true

                }
                Thread.sleep(100)
            }
            return locationResult
        }

    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}