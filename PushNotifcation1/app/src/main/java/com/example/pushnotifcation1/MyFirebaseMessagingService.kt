package com.example.pushnotifcation1

import android.util.Log
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
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}