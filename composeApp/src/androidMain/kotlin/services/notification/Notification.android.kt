package services.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.datetime.Instant
import org.koin.java.KoinJavaComponent.inject
import services.notification.NotificationReceiverIntentKeys.CHANNEL_ID
import services.notification.NotificationReceiverIntentKeys.NOTIFICATION_ID
import services.notification.NotificationReceiverIntentKeys.TEXT
import services.notification.NotificationReceiverIntentKeys.TITLE

actual fun scheduleNotification(
    id: Int,
    title: String,
    text: String,
    timestamp: Instant,
    platformOptions: PlatformNotificationOptions,
) {
    val ctx: Context by inject(Context::class.java)
    // TODO: Check if app is in allowlist for background notifications
    val receiverIntent = Intent(ctx, NotificationReceiver::class.java).apply {
        putExtra(NOTIFICATION_ID, id)
        putExtra(CHANNEL_ID, platformOptions.androidChannelId)
        putExtra(TITLE, title)
        putExtra(TEXT, text)
    }
    val pendingIntent =
        PendingIntent.getBroadcast(ctx, id, receiverIntent, PendingIntent.FLAG_MUTABLE)
    ctx.getSystemService(AlarmManager::class.java)
        .set(AlarmManager.RTC_WAKEUP, timestamp.toEpochMilliseconds(), pendingIntent)
}

fun createNotificationChannel(
    id: String,
    name: String,
    descriptionText: String,
    importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    val context: Context by inject(Context::class.java)
    val notificationManager = getSystemService(context, NotificationManager::class.java)
    val existingChannel = notificationManager?.getNotificationChannel(id)
    if (existingChannel != null) return
    val channel = NotificationChannel(id, name, importance)
    channel.description = descriptionText
    notificationManager?.createNotificationChannel(channel)
}