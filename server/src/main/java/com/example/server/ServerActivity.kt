package com.example.server

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.server.utils.Constant.ACCESS_COARSE_LOCATION
import com.example.server.utils.Constant.ACCESS_FINE_LOCATION
import com.example.server.utils.Constant.MSG_LOCATION_NOT_GRANTED
import com.example.server.utils.isGranted

class ServerActivity : AppCompatActivity() {
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        if (!permission.getOrDefault(ACCESS_FINE_LOCATION, false) || !permission.getOrDefault(
                ACCESS_COARSE_LOCATION, false
            )
        ) {
            toast(MSG_LOCATION_NOT_GRANTED)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        checkLocationPermission()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkLocationPermission() {
        when {
            !isGranted(ACCESS_FINE_LOCATION) || !isGranted(ACCESS_COARSE_LOCATION) -> locationPermissionRequest.launch(
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            )
        }
    }
}