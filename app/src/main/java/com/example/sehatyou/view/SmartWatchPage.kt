package com.example.sehatyou.view

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sehatyou.R
import com.example.sehatyou.model.Smartwatch
import com.example.sehatyou.viewmodel.SmartwatchViewModel



@Composable
fun SmartWatchPage(navController: NavController = rememberNavController()) {
    val viewModel: SmartwatchViewModel = viewModel()
    val smartwatchList by viewModel.smartwatches.collectAsState()
    val context = LocalContext.current

    var permissionsGranted by remember { mutableStateOf(false) }

    // Launcher untuk meminta mengaktifkan Bluetooth
    val bluetoothEnableLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        if (viewModel.bluetoothAdapter?.isEnabled == true) {
            // Bluetooth telah diaktifkan
            // Jika sebelumnya belum ada perangkat, user dapat mulai scan
        } else {
            // Bluetooth belum diaktifkan
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.F5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SmartWatch",
                modifier = Modifier.weight(1f),
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = { navController.popBackStack() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .size(48.dp)
                    .shadow(4.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        }

        // Konten
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(colorResource(id = R.color.FFDEC5))
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Daftar Smartwatch
            if (smartwatchList.isEmpty()) {
                // Tidak ada perangkat terhubung atau terdeteksi
                Text(
                    text = "Tidak ada perangkat yang terhubung.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Gray
                )
            } else {
                // Tampilkan perangkat yang ditemukan atau terhubung
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(smartwatchList) { smartwatch ->
                        SmartwatchContainer(
                            productName = smartwatch.name,
                            brand = smartwatch.address,
                            status = smartwatch.status,
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            onConnect = {
                                // Jika belum terhubung, coba hubungkan
                                if (!smartwatch.isConnected) {
                                    // Cari device dari bluetoothAdapter
                                    val device = viewModel.bluetoothAdapter?.getRemoteDevice(smartwatch.address)
                                    viewModel.connectToDevice(device)
                                }
                            }
                        )
                    }
                }
            }

            // Tombol Pindai Perangkat Baru - hanya muncul jika belum ada yang terhubung
            if (smartwatchList.none { it.isConnected }) {
                Button(
                    onClick = {
                        if (permissionsGranted) {
                            if (viewModel.bluetoothAdapter?.isEnabled == false) {
                                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                bluetoothEnableLauncher.launch(enableBtIntent)
                            } else {
                                viewModel.startBleScan()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    enabled = permissionsGranted
                ) {
                    Text(text = "Pindai Perangkat Baru")
                }
            }

            // Menampilkan UI Permintaan Izin jika belum diberikan
            if (!permissionsGranted) {
                RequestBluetoothPermissions {
                    permissionsGranted = true
                    // Setelah izin diberikan, baru panggil cek perangkat
                    viewModel.checkForAlreadyConnectedDevices()
                }
            } else {
                // Jika izin sudah diberikan sebelumnya
                viewModel.checkForAlreadyConnectedDevices()
            }

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopScan()
        }
    }
}

@Composable
fun SmartwatchContainer(
    productName: String,
    brand: String,
    status: String,
    modifier: Modifier = Modifier,
    onConnect: () -> Unit
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text(productName, color = Color.Black, style = MaterialTheme.typography.titleMedium)
                Text(brand, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
                Text(
                    status,
                    color = if (status.contains("Terhubung")) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = onConnect) {
                Text(text = "Hubungkan")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmartwatchContainerPrev() {
    SmartwatchContainer(
        productName = "Galaxy Watch Active2",
        brand = "AA:BB:CC:DD:EE:FF",
        status = "Terhubung, HR: 75 bpm",
        onConnect = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PrevSmartwatchPage() {
    SmartWatchPage()
}
