package com.example.androidservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CounterService : Service() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)// Coroutine scope cho service
    private  var counter = 0
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "CounterServiceChannel"
        const val NOTIFICATION_ID = 1
        const val ACTION_STOP_SERVICE = "com.example.androidservice.ACTION_STOP_SERVICE"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("CounterService", "Service onCreate")
        createNotificationChannel()
    }

    // Hàm này được gọi khi service được khởi chạy bằng startService() hoặc startForegroundService()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("CounterService", "Service onStartCommand")
        // Kiểm tra nếu là action dừng service từ notification
        if (intent?.action == ACTION_STOP_SERVICE) {
            stopSelf() // Dừng service
            return START_NOT_STICKY // Không cần khởi động lại
        }

        // Tạo và hiển thị notification, sau đó gọi startForeground
        val notification = createNotification(counter)
        startForeground(NOTIFICATION_ID, notification) // Bắt buộc cho Foreground Service

        // Bắt đầu công việc trong nền (đếm giây) bằng Coroutine
        serviceScope.launch {
            while (isActive) { // Chạy cho đến khi coroutine bị hủy
                delay(1000) // Chờ 1 giây
                counter++
                Log.d("CounterService", "Counter: $counter")
                // Cập nhật notification với giá trị counter mới
                val updatedNotification = createNotification(counter)
                notificationManager.notify(NOTIFICATION_ID, updatedNotification)
            }
        }

        // START_STICKY: Nếu service bị hệ thống kill, hệ thống sẽ cố gắng khởi động lại nó
        // và gọi lại onStartCommand với intent là null.
        return START_STICKY
    }

    // Hàm này được gọi khi service bị dừng (ví dụ: gọi stopSelf() hoặc stopService())
    override fun onDestroy() {
        super.onDestroy()
        Log.d("CounterService", "Service onDestroy")
        serviceJob.cancel() // Hủy tất cả coroutine con khi service bị hủy
    }

    // Đối với Started Service không cần ràng buộc (bind), trả về null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Tạo Notification Channel (Bắt buộc từ Android 8.0 Oreo - API 26 trở lên)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Counter Service Channel", // Tên hiển thị cho người dùng trong cài đặt
                NotificationManager.IMPORTANCE_LOW // Mức độ ưu tiên (LOW để ít làm phiền)
            )
            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

    // Tạo Notification để hiển thị
    private fun createNotification(count: Int): Notification {
        // Tạo Intent để dừng service khi nhấn nút trên notification
        val stopSelfIntent = Intent(this, CounterService::class.java).apply {
            action = ACTION_STOP_SERVICE
        }
        val stopSelfPendingIntent: PendingIntent = PendingIntent.getService(
            this, 0, stopSelfIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE // FLAG_IMMUTABLE là bắt buộc từ Android 12
        )

        // Tạo Intent để mở MainActivity khi nhấn vào notification (tùy chọn)
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Counter Service Đang Chạy")
            .setContentText("Đếm: $count")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Thay bằng icon của bạn
            .setContentIntent(pendingIntent) // Intent khi nhấn vào notification
            .addAction(R.drawable.ic_stop, "Dừng", stopSelfPendingIntent) // Thêm nút dừng
            .setOnlyAlertOnce(true) // Chỉ rung/phát âm thanh lần đầu
            .setOngoing(true) // Không thể vuốt để xóa notification
            .build()
    }
}