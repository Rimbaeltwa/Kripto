// MyFirebaseMessagingService.kt
package com.anadolubank.kripto.notification.FirebaseNotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.anadolubank.kripto.MainActivity // Kendi MainActivity'nizle değiştirin
import com.anadolubank.kripto.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Uygulama ön plandayken bir bildirim geldiğinde burası tetiklenir.
        // Uygulama arka plandayken veya kapalıyken, bildirim otomatik olarak sistem tepsisinde gösterilir.
        // Veri mesajları her zaman burada işlenir.
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Mesajın veri yükünü kontrol edin
        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            // Burada gelen veriyi işleyebilirsiniz (örneğin, bir veritabanına kaydetme, UI güncelleme vb.)
        }

        // Mesajın bildirim yükünü kontrol edin
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        // Cihazın kayıt jetonu (token) yenilendiğinde burası tetiklenir.
        // Bu jetonu sunucunuza göndermeniz gerekebilir.
        Log.d(TAG, "Refreshed token: $token")

        // Token'ı uygulamanızın arka uç sunucusuna gönderin.
        sendRegistrationToServer(token)
    }

    private fun sendNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "Firebase_Channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stat_notification) // Kendi bildirim ikonunuzu ayarlayın
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android Oreo ve üzeri için bildirim kanalı oluşturun
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Kanal Açıklaması",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Bu jetonu uygulamanızın arka uç sunucusuna gönderin.
        // Genellikle bu jeton, belirli kullanıcılara veya cihazlara bildirim göndermek için kullanılır.
        Log.d(TAG, "Sending token to server: $token")
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}