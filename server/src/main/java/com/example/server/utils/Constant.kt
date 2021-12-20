package com.example.server.utils

import android.Manifest

object Constant {
    const val MSG_LOCATION_NOT_GRANTED = "Please grant location permission to service can work"
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val DEFAULT_LOCATION = "None of Location"
}