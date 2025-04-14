package com.example.pushnotifcation1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    /**
     * Được gọi khi FCM token được tạo mới hoặc cập nhật.
     * Token này dùng để định danh thiết bị.
     * @param token Token mới.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        super.onNewToken(token)
        // TODO: Gửi token này lên server của bạn để lưu trữ và sử dụng sau này
        // sendRegistrationToServer(token)
    }

    /**
     * Được gọi khi có tin nhắn mới.
     * @param remoteMessage Đối tượng đại diện cho tin nhắn nhận được từ FCM.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Handle the received message here
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Kiểm tra xem tin nhắn có chứa payload dữ liệu không.
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            // Xử lý data payload ở đây (ví dụ: cập nhật UI, lưu trữ dữ liệu)
            // Bạn có thể tự tạo Notification từ data payload nếu muốn tùy chỉnh hoàn toàn
        }

        // Kiểm tra xem tin nhắn có chứa payload thông báo không.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Thông báo này sẽ tự động hiển thị trên thanh trạng thái
            // khi ứng dụng ở background. Khi ở foreground, bạn cần tự xử lý hiển thị ở đây nếu muốn.
            // Ví dụ: Hiển thị một dialog, hoặc tự tạo một notification tùy chỉnh
            // showCustomNotification(it.title, it.body)
        }
        showCustomNotification(
            title = remoteMessage.notification?.title,
            body = remoteMessage.notification?.body
        )
    }
    // (Tùy chọn) Hàm ví dụ để hiển thị thông báo tùy chỉnh khi ở foreground

    private fun showCustomNotification(title: String?, body: String?) {
        val channelId = "default_channel_id"
        val channelName = "Default Channel"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Tạo notification channel (cần cho Android 8.0 Oreo trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Thay bằng icon của bạn
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true) // Tự đóng khi người dùng nhấp vào

        // Hiển thị thông báo
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}