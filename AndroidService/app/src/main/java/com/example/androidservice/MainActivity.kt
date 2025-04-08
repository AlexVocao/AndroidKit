package com.example.androidservice

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.example.androidservice.ui.theme.AndroidServiceTheme
import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Đã cấp quyền Notification!", Toast.LENGTH_SHORT).show()
                startCounterService() // Gọi lại sau khi có quyền
            } else {
                Toast.makeText(this, "Không thể hiển thị Notification nếu không có quyền.", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidServiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun checkNotificationPermissionAndStartService() {
        // Kiểm tra quyền POST_NOTIFICATIONS từ Android 13 (API 33) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Quyền đã được cấp
                    startCounterService()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(this, POST_NOTIFICATIONS) -> {
                    // Hiển thị giải thích tại sao cần quyền (nếu muốn)
                    Toast.makeText(this, "Cần quyền Notification để hiển thị trạng thái Service.", Toast.LENGTH_LONG).show()
                    // Sau đó yêu cầu lại
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
                else -> {
                    // Yêu cầu quyền lần đầu
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        } else {
            // Không cần xin quyền cho các phiên bản cũ hơn
            startCounterService()
        }
    }

    private fun startCounterService() {
        val serviceIntent = Intent(this, CounterService::class.java)
        // Truyền dữ liệu vào service nếu cần
        // serviceIntent.putExtra("some_key", "some_value")

        // Sử dụng startForegroundService cho Android 8.0 (API 26) trở lên
        // Service phải gọi startForeground() trong vòng vài giây sau khi được gọi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        Toast.makeText(this, "Đã yêu cầu bắt đầu Service", Toast.LENGTH_SHORT).show()
    }

    private fun stopCounterService() {
        val serviceIntent = Intent(this, CounterService::class.java)
        stopService(serviceIntent)
        Toast.makeText(this, "Đã yêu cầu dừng Service", Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun Greeting(modifier: Modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Button(onClick = {checkNotificationPermissionAndStartService()}) {
                Text("Start")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { stopCounterService() }) {
                Text("Stop")
            }
        }
    }
}




