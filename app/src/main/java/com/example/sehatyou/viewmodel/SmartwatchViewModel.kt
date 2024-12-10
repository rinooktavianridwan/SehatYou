package com.example.sehatyou.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatyou.model.Smartwatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class SmartwatchViewModel(application: Application) : AndroidViewModel(application) {

    private val _smartwatches = MutableStateFlow<List<Smartwatch>>(emptyList())
    val smartwatches: StateFlow<List<Smartwatch>> = _smartwatches

    val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getApplication<Application>().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val foundDevices = mutableListOf<Smartwatch>()
    private var bluetoothGatt: BluetoothGatt? = null

    // UUID standar untuk layanan Heart Rate
    private val HEART_RATE_SERVICE_UUID: UUID = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")
    private val HEART_RATE_MEASUREMENT_CHAR_UUID: UUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb")

    // Nama target Smartwatch yang ingin kita deteksi jika sudah pernah terhubung
    private val targetDeviceName = "Galaxy Watch Active2"

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                @Suppress("DEPRECATION")
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    if (it.name != null && !foundDevices.any { sw -> sw.address == it.address }) {
                        foundDevices.add(Smartwatch(name = it.name, address = it.address))
                        _smartwatches.value = foundDevices.toList()
                    }
                }
            }
        }
    }

    init {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        getApplication<Application>().registerReceiver(receiver, filter)
        checkForAlreadyConnectedDevices()
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().unregisterReceiver(receiver)
        val context = getApplication<Application>().applicationContext
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Jika izin belum diberikan, Anda dapat mengabaikan penutupan GATT atau
            // melakukan penanganan lain sesuai kebutuhan.
            return
        }
        bluetoothGatt?.close()
    }


    @SuppressLint("MissingPermission")
    fun checkForAlreadyConnectedDevices() {
        val context = getApplication<Application>().applicationContext
        // Pastikan izin sudah diberikan
        val hasBluetoothConnectPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        if (!hasBluetoothConnectPermission) {
            // Izin belum diberikan, jangan lanjutkan
            return
        }

        // Memeriksa apakah ada perangkat yang telah dipasangkan (bonded) sesuai target
        val bondedDevices = bluetoothAdapter?.bondedDevices.orEmpty()
        val foundBondedWatch = bondedDevices.find { it.name == targetDeviceName }

        if (foundBondedWatch != null) {
            connectToDevice(foundBondedWatch)
        } else {
            // Tidak ada yang bonded sesuai target
        }
    }

    @SuppressLint("MissingPermission")
    fun startBleScan() {
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext

            val hasBluetoothScanPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED

            val hasLocationPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (hasBluetoothScanPermission && hasLocationPermission) {
                val scanner = bluetoothAdapter?.bluetoothLeScanner
                if (scanner == null) {
                    // Bluetooth LE scanner tidak tersedia
                    return@launch
                }

                foundDevices.clear()
                _smartwatches.value = foundDevices.toList()

                val scanCallback = object : ScanCallback() {
                    override fun onScanResult(callbackType: Int, result: ScanResult?) {
                        super.onScanResult(callbackType, result)
                        result?.device?.let { device ->
                            val deviceName = device.name
                            if (deviceName != null && !foundDevices.any { it.address == device.address }) {
                                foundDevices.add(Smartwatch(name = deviceName, address = device.address))
                                _smartwatches.value = foundDevices.toList()
                            }
                        }
                    }

                    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
                        super.onBatchScanResults(results)
                        results?.forEach { result ->
                            val device = result.device
                            val deviceName = device.name
                            if (deviceName != null && !foundDevices.any { it.address == device.address }) {
                                foundDevices.add(Smartwatch(name = deviceName, address = device.address))
                            }
                        }
                        _smartwatches.value = foundDevices.toList()
                    }

                    override fun onScanFailed(errorCode: Int) {
                        super.onScanFailed(errorCode)
                        // Tangani kesalahan scan
                    }
                }

                // Mulai scan dengan filter dan settings sesuai kebutuhan
                scanner.startScan(null, ScanSettings.Builder().build(), scanCallback)

                // Hentikan scan setelah jangka waktu tertentu (misalnya 10 detik)
                viewModelScope.launch {
                    kotlinx.coroutines.delay(10000)
                    scanner.stopScan(scanCallback)
                }
            }
        }
    }
//    fun startScan() {
//        viewModelScope.launch {
//            try {
//                val context = getApplication<Application>().applicationContext
//                val hasBluetoothScanPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                    ContextCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.BLUETOOTH_SCAN
//                    ) == PackageManager.PERMISSION_GRANTED
//                } else {
//                    ContextCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.BLUETOOTH
//                    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.BLUETOOTH_ADMIN
//                    ) == PackageManager.PERMISSION_GRANTED
//                }
//
//                val hasBluetoothConnectPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                    ContextCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.BLUETOOTH_CONNECT
//                    ) == PackageManager.PERMISSION_GRANTED
//                } else {
//                    true
//                }
//
//                val hasLocationPermission = ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//
//                if (hasBluetoothScanPermission && hasBluetoothConnectPermission && hasLocationPermission) {
//                    bluetoothAdapter?.let { adapter ->
//                        if (!adapter.isEnabled) {
//                            return@launch
//                        }
//                        if (adapter.isDiscovering) {
//                            adapter.cancelDiscovery()
//                        }
//                        foundDevices.clear()
//                        _smartwatches.value = foundDevices.toList()
//                        adapter.startDiscovery()
//                    }
//                }
//            } catch (e: SecurityException) {
//                e.printStackTrace()
//            }
//        }
//    }


    @SuppressLint("MissingPermission")
    fun stopScan() {
        viewModelScope.launch {
            bluetoothAdapter?.cancelDiscovery()
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice?) {
        device?.let { btDevice ->
            viewModelScope.launch {
                try {
                    bluetoothAdapter?.cancelDiscovery()
                    // Koneksi menggunakan GATT (BLE)
                    bluetoothGatt = btDevice.connectGatt(getApplication(), false, gattCallback)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices()

                val index = foundDevices.indexOfFirst { it.address == gatt.device.address }
                if (index != -1) {
                    foundDevices[index] = foundDevices[index].copy(isConnected = true, status = "Terhubung, menunggu data...")
                    _smartwatches.value = foundDevices.toList()
                } else {
                    // Jika perangkat belum ada di foundDevices (mis. dari bonded list), tambahkan
                    foundDevices.add(Smartwatch(name = gatt.device.name ?: "Unknown", address = gatt.device.address, isConnected = true, status = "Terhubung, menunggu data..."))
                    _smartwatches.value = foundDevices.toList()
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                val index = foundDevices.indexOfFirst { it.address == gatt.device.address }
                if (index != -1) {
                    foundDevices[index] = foundDevices[index].copy(isConnected = false, status = "Tidak terhubung")
                    _smartwatches.value = foundDevices.toList()
                }
                gatt.close()
            }
        }

//        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
//            super.onServicesDiscovered(gatt, status)
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                val heartRateService = gatt.getService(HEART_RATE_SERVICE_UUID)
//                if (heartRateService != null) {
//                    val heartRateCharacteristic = heartRateService.getCharacteristic(HEART_RATE_MEASUREMENT_CHAR_UUID)
//                    if (heartRateCharacteristic != null) {
//                        if (ActivityCompat.checkSelfPermission(
//                                this,
//                                Manifest.permission.BLUETOOTH_CONNECT
//                            ) != PackageManager.PERMISSION_GRANTED
//                        ) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return
//                        }
//                        gatt.setCharacteristicNotification(heartRateCharacteristic, true)
//                        val descriptor = heartRateCharacteristic.getDescriptor(
//                            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
//                        )
//                        if (descriptor != null) {
//                            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
//                            gatt.writeDescriptor(descriptor)
//                            Log.d("GATT", "Descriptor written for notifications")
//                        } else {
//                            Log.d("GATT", "Descriptor not found")
//                        }
//                    } else {
//                        Log.d("GATT", "Heart rate characteristic not found")
//                    }
//                } else {
//                    Log.d("GATT", "Heart rate service not found")
//                }
//            } else {
//                Log.d("GATT", "Service discovery failed with status $status")
//            }
//        }


        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            if (characteristic.uuid == HEART_RATE_MEASUREMENT_CHAR_UUID) {
                val heartRate = parseHeartRate(characteristic.value)
                Log.d("GATT", "Heart rate received: $heartRate bpm")
            } else {
                Log.d("GATT", "Data received from characteristic: ${characteristic.uuid}")
            }
        }

    }

    private fun parseHeartRate(data: ByteArray?): Int {
        if (data == null || data.size < 2) {
            return 0 // tidak ada data
        }
        return data[1].toInt() and 0xFF
    }
}
