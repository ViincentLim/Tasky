package services.notification

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tasky.MainActivity
import org.koin.java.KoinJavaComponent.inject
import services.notification.NotificationReceiverIntentKeys.CHANNEL_ID
import services.notification.NotificationReceiverIntentKeys.NOTIFICATION_ID
import services.notification.NotificationReceiverIntentKeys.TEXT
import services.notification.NotificationReceiverIntentKeys.TITLE

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val channelId = intent.getStringExtra(CHANNEL_ID) ?: "default"
        val title = intent.getStringExtra(TITLE)
        val text = intent.getStringExtra(TEXT)

        val activityIntent = try {
            val mainActivity: MainActivity by inject(MainActivity::class.java)
            mainActivity.intent
        } catch (e: IllegalStateException) {
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
        val contentIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle(title)
            .setContentText(text).setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent).setAutoCancel(true).build()

        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }
}