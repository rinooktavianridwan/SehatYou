package com.example.sehatyou.view

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestBluetoothPermissions(onPermissionsGranted: () -> Unit) {
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissions
    )

    LaunchedEffect(key1 = permissionsState.allPermissionsGranted) {
        if (permissionsState.allPermissionsGranted) {
            onPermissionsGranted()
        }
    }

    // Jika belum diberikan izin, tampilkan UI permintaan
    if (!permissionsState.allPermissionsGranted) {
        Column {
            permissionsState.permissions.forEach { perm ->
                when {
                    perm.status.shouldShowRationale -> {
                        Text("Aplikasi membutuhkan izin untuk memindai dan menghubungkan perangkat Bluetooth.")
                    }
                }
            }

            Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                Text("Minta Izin Bluetooth")
            }
        }
    }
}
